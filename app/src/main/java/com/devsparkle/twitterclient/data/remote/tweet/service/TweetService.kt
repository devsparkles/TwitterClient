package com.devsparkle.twitterclient.data.remote.tweet.service

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface TweetService {

    @GET("/2/tweets/search/stream")
    @Streaming
    fun getTweets(): Call<ResponseBody>


}