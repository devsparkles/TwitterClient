package com.devsparkle.twitterclient.domain.model

import java.util.Date

data class Tweet(
    val id: String?,
    val tweetId: String?,
    val text: String?,
    var lifespan: Date?
)