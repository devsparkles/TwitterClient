package com.devsparkle.twitterclient.data.local.tweet.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.devsparkle.twitterclient.data.local.tweet.entities.TweetEntity
import com.devsparkle.twitterclient.domain.model.Tweet

/**
 * Data Access Object for the Tweets table.
 */
@Dao
interface TweetDao {

    /***
     * Find by text
     */
    @Query("SELECT * FROM tweets WHERE text=:text")
    fun findByText(text : String): List<TweetEntity>

    /**
     * Observe tweets
     */
    @Query("SELECT * FROM tweets")
    fun observeTweets(): LiveData<List<TweetEntity>>


    /**
     * Get the list of tweets.
     *
     * @return all tweets.
     */
    @Query("SELECT * FROM tweets")
    fun getTweets(): List<TweetEntity>


    /**
     * Insert a tweet in the database. If the tweet already exists, replace it.
     *
     * @param tweets the tweet to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTweets(tweet: TweetEntity)


    /**
     * Insert a tweets in the database. If the tweet already exists, replace it.
     *
     * @param tweets the tweet to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTweets(vararg tweets: TweetEntity)

    /**
     * Delete all tweets.
     */
    @Query("DELETE FROM tweets")
    suspend fun deleteTweets()


    /**
     * Delete tweet
     */
    @Delete
    fun deleteTweet(tweet: TweetEntity): Int
}