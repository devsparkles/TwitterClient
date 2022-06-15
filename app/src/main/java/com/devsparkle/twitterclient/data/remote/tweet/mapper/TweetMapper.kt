package com.devsparkle.twitterclient.data.remote.tweet.mapper

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetDto
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.model.TweetWrapper

fun Resource<TweetWrapperDto?>.toDomain(): Resource<TweetWrapper> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.value().toDomain())
        is Resource.SuccessWithoutContent -> Resource.SuccessWithoutContent()
        is Resource.Error -> Resource.Error(this.error())
        is Resource.Loading -> Resource.Loading()
    }
}

fun List<TweetDto>.toDomain(): MutableList<Tweet> {
    val result: MutableList<Tweet> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result
}


fun TweetWrapperDto?.toDomain(): TweetWrapper {
    return TweetWrapper(
        this?.data?.toDomain(),
    )
}


fun TweetDto?.toDomain(): Tweet {
    return Tweet(
        this?.id,
        this?.text,
        lifespan = null
    )
}


fun Tweet.toEntity(): TweetEntity {
    return TweetEntity(
        tweetId = this.id ?: "",
        text = this.text ?: "",
        lifespan = this.lifespan
    )
}


fun TweetEntity?.toDomain(): Tweet {
    return Tweet(
        this?.id,
        this?.text,
        this?.lifespan
    )
}

fun List<TweetEntity>.toDomainTweets(): List<Tweet> {
    val result: MutableList<Tweet> = mutableListOf()
    this.forEach { result.add(it.toDomain()) }
    return result.toList()
}
