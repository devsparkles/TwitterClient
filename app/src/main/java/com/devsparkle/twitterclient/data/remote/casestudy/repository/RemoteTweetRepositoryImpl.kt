package com.devsparkle.twitterclient.data.remote.casestudy.repository

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.casestudy.service.TweetService
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import timber.log.Timber

class RemoteTweetRepositoryImpl(private val service: TweetService) :
    RemoteTweetRepository {

    override suspend fun getTweets(query: String): Resource<List<Tweet>?> {
        try {
            val result = service.getTweets(query)
            return Resource.of {
                result.toDomain().tweets
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching tweets")
        }
        return Resource.Error()
    }
}