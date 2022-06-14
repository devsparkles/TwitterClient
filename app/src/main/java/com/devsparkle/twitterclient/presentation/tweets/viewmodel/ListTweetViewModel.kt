package com.devsparkle.twitterclient.presentation.tweets.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.DeleteAllTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.ObserveTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweet
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ListTweetViewModel(
    private val getTweetLifespan: GetTweetLifeSpan,
    private val deleteAllTweets: DeleteAllTweets,
    private val observerTweets: ObserveTweets,
    private val persistTweet: PersistTweet,
    private val getTweets: GetTweets,
    private val coroutineContext: CoroutineDispatcher
) : ViewModel() {


    fun observeTweetFromLocal(): LiveData<List<Tweet>> {
        return observerTweets.invoke()
    }

    fun searchTweetsByQuery(query: String, isConnected: Boolean) {
        viewModelScope.launch(coroutineContext) {

            withContext(Dispatchers.IO) {
                // get from remote
                val r = getTweets.invoke(query)
                if (r.isNotAnError()) {
                    // delete all previous tweet
                    deleteAllTweets.invoke()

                    // save in the local database with correct lifespan

                    var lastDateInserted: Date? = null

                    r.value()?.let { tweets ->
                        tweets.forEach { tweet ->
                            val interval = getTweetLifespan.invoke()
                            if (lastDateInserted == null) {
                                lastDateInserted = Date().addSeconds(interval)
                                tweet.lifespan = lastDateInserted
                                persistTweet.invoke(tweet)
                            } else {
                                lastDateInserted = lastDateInserted!!.addSeconds(interval)
                                tweet.lifespan = lastDateInserted
                                persistTweet.invoke(tweet)
                            }

                        }
                    }
                }
            }
        }
    }

}