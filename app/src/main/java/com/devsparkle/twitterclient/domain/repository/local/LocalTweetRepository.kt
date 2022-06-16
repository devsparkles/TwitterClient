package com.devsparkle.twitterclient.domain.repository.local

import androidx.lifecycle.LiveData
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import org.w3c.dom.Text

interface LocalTweetRepository {
    suspend fun getTweets():  Resource<List<Tweet>?>
    fun observeTweets(): LiveData<List<Tweet>>
    suspend fun persistTweets(list: List<Tweet>)
    suspend fun persistTweet(tweet:Tweet)
    suspend fun deleteAllTweets()
    fun deleteTweet(tweet: Tweet) : Int
    suspend fun defineTweetLifeSpan(tweetLifeSpan: Int)
    suspend fun getTweetLifeSpan(): Int
}