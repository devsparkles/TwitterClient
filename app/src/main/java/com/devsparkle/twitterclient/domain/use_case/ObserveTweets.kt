package com.devsparkle.twitterclient.domain.use_case

import androidx.lifecycle.LiveData
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository

class ObserveTweets(
    private val localTweetRepository: LocalTweetRepository
) {
    operator fun invoke(): LiveData<List<Tweet>> {
        return localTweetRepository.observeTweets()
    }
}
