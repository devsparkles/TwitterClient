package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.devsparkle.twitterclient.base.BaseViewModel
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetObservableTweets
import com.devsparkle.twitterclient.domain.use_case.SearchAndSaveTweets
import com.devsparkle.twitterclient.presentation.tweets.worker.DeleteTweetWorker
import com.devsparkle.twitterclient.presentation.tweets.worker.DeleteTweetWorker.Companion.DELETE_OLD_TWEET
import com.devsparkle.twitterclient.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class ListTweetViewModel(
    private val app: Application,
    private val configureTweetLifeSpan: ConfigureTweetLifeSpan,
    private val searchAndSaveTweets: SearchAndSaveTweets,
    private val getObservableTweets: GetObservableTweets,
) : BaseViewModel() {

    private val _remoteTweetState = MutableLiveData<Resource<List<Tweet>?>>()
    val remoteTweetState: LiveData<Resource<List<Tweet>?>> = _remoteTweetState


    private val workManager = WorkManager.getInstance(app)

    internal fun cancelWork() {
        workManager.cancelUniqueWork(DELETE_OLD_TWEET)
    }

    fun launchDeleteOutdatedTweetJob() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work =
            PeriodicWorkRequestBuilder<DeleteTweetWorker>(5, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()
        val workManager = WorkManager.getInstance(app)
        workManager.enqueueUniquePeriodicWork(
            DELETE_OLD_TWEET,
            ExistingPeriodicWorkPolicy.REPLACE,
            work
        )
    }

    /**
     * Give 10 secondes of lifetime for each tweet
     */
    fun configureTweetLifespan(interval: Int) {
        viewModelScope.launch {
            configureTweetLifeSpan.invoke(interval)
        }
    }

    /**
     * Observe tweets from the local database
     */
    fun observableTweets(): LiveData<List<Tweet>> {
        return getObservableTweets.invoke()
    }

    /***
     * Search tweets by query,
     * Delete all previous tweets in the local database, #
     * Save new Tweets with an interval
     */
    fun searchTweetsByQuery(query: String) {
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                _remoteTweetState.postValue(Resource.Loading())
                withContext(Dispatchers.IO) {
                    wrapEspressoIdlingResource {
                        val response = searchAndSaveTweets.invoke(query)
                        if (response.isAnError()) {
                            _remoteTweetState.postValue(Resource.Error())
                        }
                    }
                }
            }
        }
    }

}