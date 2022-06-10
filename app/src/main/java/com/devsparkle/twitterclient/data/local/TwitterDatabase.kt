package com.devsparkle.twitterclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity

/**
 * The Room Database that contains the Case Study table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [TweetEntity::class], version = 2, exportSchema = false)
abstract class TwitterDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao
}
