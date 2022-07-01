package com.devsparkle.twitterclient

import app.cash.turbine.test
import co.infinum.retromock.Retromock
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.services.MockTweetService
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.koin.test.mock.MockProviderRule
import org.mockito.Mockito


@ExperimentalCoroutinesApi
class RemoteTweetRepositoryTest {


    @Test
    fun `Remote repository should get the same amount of data that it received from the service`() {
        runBlocking {

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

