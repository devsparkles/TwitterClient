package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.utils.extensions.IsFutureDate
import timber.log.Timber

class DeleteOldTweet(private val localTweetRepository: LocalTweetRepository) {

    operator fun invoke() {
        try {
            Timber.d("start periodicallydeletetweet")
            val t = localTweetRepository.getTweets()
            val listOfTweetsToDelete = tweetsToDelete(t)
            listOfTweetsToDelete.forEach { tweet ->
                val numberOfDeletedLine = localTweetRepository.deleteTweet(tweet)
                Timber.d("delete tweet ${tweet.id} value $numberOfDeletedLine")
            }
            Timber.d("success delete ${listOfTweetsToDelete.size}  outdated tweet")

        } catch (e: Exception) {
            Timber.d("delete tweet exception ${e.message}")
        }
    }

    private fun tweetsToDelete(list: List<Tweet>): List<Tweet> {
        val result = mutableListOf<Tweet>()
        list.forEach { tweet ->
            tweet.lifespan?.let { lifespan ->
                if (!lifespan.IsFutureDate()) {
                    result.add(tweet)
                }
            }
        }
        return result
    }
}