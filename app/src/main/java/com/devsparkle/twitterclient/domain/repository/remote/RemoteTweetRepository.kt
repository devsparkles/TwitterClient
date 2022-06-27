package com.devsparkle.twitterclient.domain.repository.remote

import okhttp3.ResponseBody
import retrofit2.Call

interface RemoteTweetRepository {
    suspend fun getTweets(): Call<ResponseBody>?
}