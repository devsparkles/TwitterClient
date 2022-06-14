package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository


class GetTweets(
    private val remoteTweetRepository: RemoteTweetRepository
) {
    suspend operator fun invoke(query: String): Resource<List<Tweet>?> {
            return remoteTweetRepository.getTweets(query)
    }
}