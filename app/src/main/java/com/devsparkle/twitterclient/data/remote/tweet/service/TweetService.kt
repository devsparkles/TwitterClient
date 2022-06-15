package com.devsparkle.twitterclient.data.remote.tweet.service


import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TweetService {

    @GET("/2/tweets/search/recent")
    suspend fun getTweets(@Query("query") query: String): TweetWrapperDto?

}