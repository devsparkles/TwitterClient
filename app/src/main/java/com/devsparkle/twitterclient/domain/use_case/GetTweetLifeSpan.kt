package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository

class GetTweetLifeSpan(
    private val localTweetRepository: LocalTweetRepository
) {
    /**
     * Retrieve the li
     */
    suspend operator fun invoke(): Int {
        return localTweetRepository.getTweetLifeSpan()
    }
}