package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import java.util.Date


class PersistTweetsWithLifeSpan(
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke(tweets: List<Tweet>, interval: Int) {
        var lastDateInserted: Date? = null
        for (tweet in tweets) {
            if (lastDateInserted == null) {
                lastDateInserted = Date().addSeconds(interval)
                tweet.lifespan = lastDateInserted
                localTweetRepository.persistTweet(tweet)
            } else {
                lastDateInserted = lastDateInserted.addSeconds(interval)
                tweet.lifespan = lastDateInserted
                localTweetRepository.persistTweet(tweet)
            }
        }
    }
}
