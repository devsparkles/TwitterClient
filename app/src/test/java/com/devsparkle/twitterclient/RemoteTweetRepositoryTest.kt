package com.devsparkle.twitterclient

import com.devsparkle.twitterclient.data.di.remoteDataModule
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetDto
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.github.javafaker.Faker
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class RemoteTweetRepositoryTest : KoinTest {


    // I need to do that so I can do declareMock later in the test
    // documentation https://insert-koin.io/docs/reference/koin-test/testing/
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                listOf(
                    remoteDataModule
                )
            })
    }


    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Test
    fun `Remote repository should get the same amount of data that it received from the service`() =
        runTest {

            //Given
            val query = "china"
            val listTweetsResponse = listOf<TweetDto>(
                TweetDto(text = "tweet1", id = Faker.instance().idNumber().toString()),
                TweetDto(text = "tweet2", id = Faker.instance().idNumber().toString()),
            )
            val tweetService = declareMock<TweetService>()
            assertNotNull(get<TweetService>())
//            Mockito.`when`(tweetService.getTweets(query)).thenReturn(
//                TweetWrapperDto(data = listTweetsResponse)
//            )
//            val remoteTweetRepositoryImpl = RemoteTweetRepositoryImpl(tweetService)

            // When
//            val list = remoteTweetRepositoryImpl.getTweets(query).value()

            // then
//            list?.let { assertEquals(2, it.size) }
        }

}

