package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import java.util.Date

class SearchAndSaveTweets(
    private val remoteTweetRepository: RemoteTweetRepository,
    private val localTweetRepository: LocalTweetRepository
) {
    suspend operator fun invoke(query: String): Resource<List<Tweet>> {
        val tweetsResponse = remoteTweetRepository.getTweets(query)
        if (tweetsResponse.isNotAnError()) {
            val interval = localTweetRepository.getTweetLifeSpan()
            val tweets = tweetsResponse.value()
            localTweetRepository.deleteAllTweets()
            var lastDateInserted = Date()
            if (tweets != null) {
                for (tweet in tweets) {
                    lastDateInserted = lastDateInserted.addSeconds(interval)
                    tweet.lifespan = lastDateInserted
                    localTweetRepository.persistTweet(tweet)
                }
            }
            return Resource.SuccessWithoutContent()
        } else {
            return Resource.Error()
        }
    }

}
