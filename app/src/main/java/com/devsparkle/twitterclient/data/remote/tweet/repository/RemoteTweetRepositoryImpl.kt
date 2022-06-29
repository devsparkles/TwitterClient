package com.devsparkle.twitterclient.data.remote.tweet.repository

import com.devsparkle.twitterclient.data.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.OAuthInterceptor
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.Constants
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.nio.charset.Charset

class RemoteTweetRepositoryImpl(private val client: OkHttpClient) :
    RemoteTweetRepository {

    override suspend fun getTweets(): Flow<Tweet?> {
        return flow {

            kotlin.runCatching {
                val request: Request = Request.Builder()
                    .url(Constants.TWITTER_STREAM_URL)
                    .method("GET", null)
                    .build()
                val response: Response = client.newCall(request).execute()
                val source = response.body?.source()
                val buffer = Buffer()
     //           while (!source!!.exhausted()) {
                while(true){
                    response.body?.source()?.read(buffer, 8192)
                    val data = buffer.readString(Charset.defaultCharset())
                    val tweet: TweetWrapperDto = Gson().fromJson(data, TweetWrapperDto::class.java)
                    emit(tweet.data.toDomain())
                }

            }.onFailure {
                print("error flow ${it.message}")
        //       emit(it.message.toString())
            }


        }
    }

}