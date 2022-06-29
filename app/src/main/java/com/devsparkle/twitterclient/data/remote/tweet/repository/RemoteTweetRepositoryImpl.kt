package com.devsparkle.twitterclient.data.remote.tweet.repository

import com.devsparkle.twitterclient.data.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.Buffer
import java.nio.charset.Charset


class RemoteTweetRepositoryImpl(
    private val tweetService: TweetService
) :
    RemoteTweetRepository {

    override suspend fun getTweets(): Flow<Tweet?> {
        return flow {

            kotlin.runCatching {
                val response = tweetService.getTweets().execute()
                val source = response.body()?.source()
                val buffer = Buffer()

                while (!source!!.exhausted()) {
                    response.body()?.source()?.read(buffer, 8192)
                    val data = buffer.readString(Charset.defaultCharset())

                    try {
                        val gson = GsonBuilder()
                            .setLenient()
                            .create()
                        val tweet: TweetWrapperDto =
                            gson.fromJson(data, TweetWrapperDto::class.java)
                        emit(tweet.data.toDomain())
                    } catch (e: Exception) {
                        println("error parsing or incomplete Json ${e.message}")
                    }


                }

            }.onFailure {
                print("error flow ${it.message}")
            }


        }
    }

}