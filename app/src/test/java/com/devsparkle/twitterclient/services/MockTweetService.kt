package com.devsparkle.twitterclient.services

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming

interface MockTweetService :TweetService{

    @Mock
    @GET("/2/tweets/search/stream")
    @Streaming
    @MockResponse(body = "{\"data\":{\"id\":\"1542814699959508994\",\"text\":\"tweet1\"},\"matching_rules\":[{\"id\":\"1542814594246262784\",\"tag\":\"cats with images\"}]}")
    override fun getTweets(): Call<ResponseBody>

}