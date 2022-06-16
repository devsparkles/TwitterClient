package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class GetTweets(
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke(): Resource<List<Tweet>?> {
        return localTweetRepository.getTweets()
    }
}
