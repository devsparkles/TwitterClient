package com.devsparkle.twitterclient.domain.repository.remote

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.domain.model.Tweet
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

interface RemoteTweetRepository {
    suspend fun getTweets(): Observable<ResponseBody>
}