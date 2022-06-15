package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.base.BaseViewModel
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.DeleteAllTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.SearchTweetsByQuery
import com.devsparkle.twitterclient.domain.use_case.ObserveTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweetsWithLifeSpan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.utils.wrapEspressoIdlingResource

class ListTweetViewModel(
    private val getTweetLifespan: GetTweetLifeSpan,
    private val deleteAllTweets: DeleteAllTweets,
    private val observeTweets: ObserveTweets,
    private val persistTweetsWithLifeSpan: PersistTweetsWithLifeSpan,
    private val searchTweetsByQuery: SearchTweetsByQuery
) : BaseViewModel() {

    private val _remoteTweetState = MutableLiveData<Resource<List<Tweet>?>>()
    val remoteTweetState: LiveData<Resource<List<Tweet>?>> = _remoteTweetState

    /**
     * Observe and display tweets from the local database
     */
    fun tweets(): LiveData<List<Tweet>> {
        return observeTweets.invoke()
    }

    /***
     * Search tweets by query, Delete all previous tweets from the local database, Save new Tweets with an interval
     */
    fun searchTweetsByQuery(query: String) {
        viewModelScope.launch() {
            if (isNetworkAvailable.value == true) {
                _remoteTweetState.postValue(Resource.Loading())
                withContext(Dispatchers.IO) {
                    wrapEspressoIdlingResource {
                        val response = searchTweetsByQuery.invoke(query)
                        if (response.isNotAnError()) {
                            response.value()?.let { tweets ->
                                val interval = getTweetLifespan.invoke()
                                deleteAllTweets.invoke()
                                persistTweetsWithLifeSpan.invoke(tweets, interval)
                            } ?: run {
                                _remoteTweetState.postValue(Resource.Error())
                            }
                        } else {
                            _remoteTweetState.postValue(Resource.Error())
                        }
                    }
                }
            } else {
                withContext(Dispatchers.IO) {
                    wrapEspressoIdlingResource {

                    }
                }

            }





        }
    }

}