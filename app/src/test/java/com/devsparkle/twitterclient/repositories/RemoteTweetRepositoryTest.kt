package com.devsparkle.twitterclient.repositories

import app.cash.turbine.test
import co.infinum.retromock.Retromock
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.services.MockTweetService
import com.devsparkle.twitterclient.utils.MockRemoteRetrofitBuilder
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


@ExperimentalCoroutinesApi
class RemoteTweetRepositoryTest {


    @Test
    fun `The repository should emit the same object it received`() {
        runTest{

            val retroMock = Retromock.Builder()
                .retrofit(MockRemoteRetrofitBuilder.createRetrofit("https://localhost:8080/"))
                .build()
            val mockApiService: MockTweetService = retroMock.create(MockTweetService::class.java)
            val remoteTweetRepositoryImpl = RemoteTweetRepositoryImpl(mockApiService)


            // When
            // When the repository emits a value
            remoteTweetRepositoryImpl.getTweets().test {
                // then
                assertEquals("tweet1", awaitItem()?.text)

                cancelAndIgnoreRemainingEvents()
            }

        }
    }






}

