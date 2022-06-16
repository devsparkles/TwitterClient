package com.devsparkle.twitterclient.data.local.tweet.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

/**
 * Entity for the tweet
 * @param tweetId  id
 * @param text tweet text
 * @param lifespan tweet lifespan
 */
@Entity(tableName = "tweets")
data class TweetEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "tweetId") var tweetId: String = "",
    @ColumnInfo(name = "text") var text: String = "",
    @ColumnInfo(name = "lifespan") var lifespan: Date? = Date(),
)

