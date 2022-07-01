package com.devsparkle.twitterclient.data.mapper

import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetDto
import com.devsparkle.twitterclient.domain.model.Tweet


fun TweetDto?.toDomain(): Tweet {
    return Tweet(
        id = this?.id,
        tweetId = null,
        text = this?.text,
        lifespan = null
    )
}


fun Tweet.toEntity(): TweetEntity {
    return TweetEntity(
        id = this.id ?: "",
        tweetId = this.tweetId ?: "",
        text = this.text ?: "",
        lifespan = this.lifespan
    )
}


fun TweetEntity?.toDomain(): Tweet {
    return Tweet(
        id = this?.id,
        tweetId = this?.tweetId,
        text = this?.text,
        lifespan = this?.lifespan
    )
}

fun List<TweetEntity>.toDomainTweets(): List<Tweet> {
    val result: MutableList<Tweet> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result.toList()
}

