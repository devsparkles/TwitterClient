package com.devsparkle.twitterclient.data.local.tweet.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Entity for the tweet
 * @param tweetId  id
 * @param text tweet text
 */
@Entity(tableName = "tweets")
data class TweetEntity @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "id") var tweetId: String = "",
    @ColumnInfo(name = "text") var text: String = "",
)

