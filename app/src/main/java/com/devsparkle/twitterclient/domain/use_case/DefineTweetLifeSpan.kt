package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class DefineTweetLifeSpan(
    private val localTweetRepository: LocalTweetRepository
) {
    /**
     * Define the tweetlifespan
     */
    suspend operator fun invoke(tweetLifeSpan: Int) {
        return localTweetRepository.defineTweetLifeSpan(tweetLifeSpan)
    }
}