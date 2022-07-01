package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.base.BaseViewModel
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.*
import com.devsparkle.twitterclient.utils.LogApp
import com.devsparkle.twitterclient.utils.wrapEspressoIdlingResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTweetViewModel(
    private val deleteLocalOldTweet: DeleteLocalOldTweet,
    private val configureTweetLifeSpan: ConfigureTweetLifeSpan,
    private val searchOpenAndSaveTweetStream: SearchOpenAndSaveTweetStream,
    private val getLocalTweets: GetLocalTweets,
) : BaseViewModel() {

    private val _remoteTweetState = MutableLiveData<Resource<String>>()
    val remoteTweetState: LiveData<Resource<String>> = _remoteTweetState


    fun deleteOutDatedTweet() {
        deleteLocalOldTweet()
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
        return getLocalTweets()
    }


    /***
     * Open the tweet stream and save them inside the localdabase
     */
    fun getTweetStream(rules: List<String>) {
        viewModelScope.launch {
            if (isNetworkAvailable.value == true) {
                _remoteTweetState.postValue(Resource.Loading())
                withContext(Dispatchers.IO) {
                    wrapEspressoIdlingResource {
                        val response = searchOpenAndSaveTweetStream(rules)
                        _remoteTweetState.postValue(response)
                    }
                }
            }
        }
    }
}