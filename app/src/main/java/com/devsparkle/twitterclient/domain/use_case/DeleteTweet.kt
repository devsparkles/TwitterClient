package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class DeleteTweet(
    private val localTweetRepository: LocalTweetRepository
) {
    operator fun invoke(tweet: Tweet): Int {
        return localTweetRepository.deleteTweet(tweet)
    }
}
