package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.base.BaseViewModel
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetObservableTweets
import com.devsparkle.twitterclient.domain.use_case.DeleteOldTweet
import com.devsparkle.twitterclient.domain.use_case.SearchAndSaveTweets
import com.devsparkle.twitterclient.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTweetViewModel(
    private val deleteOldTweet: DeleteOldTweet,
    private val configureTweetLifeSpan: ConfigureTweetLifeSpan,
    private val searchAndSaveTweets: SearchAndSaveTweets,
    private val getObservableTweets: GetObservableTweets,
) : BaseViewModel() {

    private val _remoteTweetState = MutableLiveData<Resource<List<Tweet>?>>()
    val remoteTweetState: LiveData<Resource<List<Tweet>?>> = _remoteTweetState


    fun deleteOutDatedTweet() {
        deleteOldTweet()
    }

    /**
     * Give 10 secondes of lifetime for each tweet
     */
    fun configureTweetLifespan(interval: Int) {
        viewModelScope.launch {
            configureTweetLifeSpan(interval)
        }
    }

    /**
     * Observe tweets from the local database
     */
    fun observableTweets(): LiveData<List<Tweet>> {
        return getObservableTweets()
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
                        val response = searchAndSaveTweets(query)
                        if (response.isAnError()) {
                            _remoteTweetState.postValue(Resource.Error())
                        }
                    }
                }
            }
        }
    }

}