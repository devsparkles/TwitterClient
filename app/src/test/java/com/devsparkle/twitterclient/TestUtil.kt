package com.devsparkle.twitterclient

import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity
import com.devsparkle.twitterclient.domain.model.Tweet
import com.github.javafaker.Faker
import java.util.Date

object TestUtil {

    fun createDatabaseTweets(nb: Int): List<TweetEntity> {
        val list = mutableListOf<TweetEntity>()

        for (i in 0..nb) {
            list.add(
                TweetEntity(
                    text = Faker.instance().company().catchPhrase(),
                    tweetId = Faker.instance().idNumber().toString(),
                    lifespan = Date()
                )
            )
        }
        return list
    }

    fun createDomainTweets(nb: Int): List<Tweet> {
        val list = mutableListOf<Tweet>()

        for (i in 0..nb) {
            list.add(
                Tweet(
                    id = Faker.instance().idNumber().toString(),
                    text = Faker.instance().company().catchPhrase(),
                    tweetId = Faker.instance().idNumber().toString(),
                    lifespan = Date()
                )
            )
        }
        return list
    }
}