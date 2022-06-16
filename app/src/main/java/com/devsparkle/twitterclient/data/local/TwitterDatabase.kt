package com.devsparkle.twitterclient.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devsparkle.twitterclient.data.local.converter.DateConverter
import com.devsparkle.twitterclient.data.local.converter.DateToStringConverter
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity

/**
 * The Room Database that contains the tweets table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [TweetEntity::class], version = 4, exportSchema = false)
@TypeConverters(DateConverter::class, DateToStringConverter::class)
abstract class TwitterDatabase : RoomDatabase() {

    abstract fun tweetDao(): TweetDao
}
