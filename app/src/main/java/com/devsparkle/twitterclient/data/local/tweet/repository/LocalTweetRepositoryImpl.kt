package com.devsparkle.twitterclient.data.local.tweet.repository

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toDomainCaseStudy
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toEntity
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class LocalTweetRepositoryImpl(private val tweetDao: TweetDao) :
    LocalTweetRepository {

    override suspend fun getCaseStudies(): Resource<List<Tweet>?> {
        return Resource.of<List<Tweet>?> {
            tweetDao.getTweets().toDomainCaseStudy()
        }
    }

    override suspend fun persistCaseStudies(list: List<Tweet>) {
        tweetDao.deleteTweets()
        list.forEach {
            tweetDao.insertTweets(it.toEntity())
        }
    }
}

