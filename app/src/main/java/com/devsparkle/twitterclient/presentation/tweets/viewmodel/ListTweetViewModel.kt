package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweets
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListTweetViewModel(
    private val persistTweets: PersistTweets,
    private val getTweets: GetTweets,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {

    private val _tweets = MutableLiveData<Resource<List<Tweet>?>>()
    val tweet: LiveData<Resource<List<Tweet>?>> = _tweets

    fun getCaseStudies(query : String, isConnected: Boolean) {
        viewModelScope.launch(coroutineContext) {
            _tweets.postValue(Resource.Loading())
            withContext(Dispatchers.IO) {
                val response = getTweets.invoke(query, isConnected)
                if (response.isNotAnError()) {
                    _tweets.postValue(response)
                } else {
                    _tweets.postValue(Resource.Error())
                }
            }
        }
    }


    fun persistCaseStudies(list: List<Tweet>) {
        viewModelScope.launch(coroutineContext) {
            withContext(Dispatchers.IO) {
                persistTweets.invoke(list)
            }
        }
    }
}