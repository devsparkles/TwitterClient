package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository


class PersistTweets(
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke(list: List<Tweet>) {
        localTweetRepository.persistCaseStudies(list)
    }
}
