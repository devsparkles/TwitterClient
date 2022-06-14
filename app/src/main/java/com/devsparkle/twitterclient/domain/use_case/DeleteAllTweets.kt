package com.devsparkle.twitterclient.domain.use_case


import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository


class DeleteAllTweets(
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke() {
        localTweetRepository.deleteAllTweets()
    }
}
