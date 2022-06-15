package com.devsparkle.twitterclient.data.local.tweet.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.remote.tweet.mapper.toDomainTweets
import com.devsparkle.twitterclient.data.remote.tweet.mapper.toEntity
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class LocalTweetRepositoryImpl(
    private val dao: TweetDao,
    private val sharedPreferences: SharedPreferences
) :
    LocalTweetRepository {

    override suspend fun getTweetLifeSpan(): Int {
        return sharedPreferences.getInt(TWEET_LIFE_SPAN_KEY, 0)
    }

    override suspend fun defineTweetLifeSpan(tweetLifeSpan: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(TWEET_LIFE_SPAN_KEY, tweetLifeSpan)
        editor.apply()
    }

    override suspend fun deleteAllTweets() {
        dao.deleteTweets()
    }

    override fun observeTweets(): LiveData<List<Tweet>> {
        val tweetsLiveData = dao.observeTweets()
        return tweetsLiveData.map { it.toDomainTweets() }
    }

    override suspend fun getCaseStudies(): Resource<List<Tweet>?> {
        return Resource.of<List<Tweet>?> {
            dao.getTweets().toDomainTweets()
        }
    }

    override suspend fun persistTweets(list: List<Tweet>) {
        dao.deleteTweets()
        list.forEach {
            dao.insertTweets(it.toEntity())
        }
    }

    override suspend fun persistTweet(tweet: Tweet) {
        dao.insertTweets(tweet.toEntity())
    }

    private companion object {
        const val TWEET_LIFE_SPAN_KEY = "TWEET_LIFE_SPAN_KEY"
    }

}

