package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository


class PersistTweet(
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke(tweet: Tweet) {
        localTweetRepository.persistTweet(tweet)
    }
}
