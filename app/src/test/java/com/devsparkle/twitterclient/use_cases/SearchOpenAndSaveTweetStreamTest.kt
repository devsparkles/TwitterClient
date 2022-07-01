package com.devsparkle.twitterclient.use_cases

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.infinum.retromock.Retromock
import com.devsparkle.twitterclient.data.local.TwitterDatabase
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.repository.LocalTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.rule.repository.RemoteRuleRepositoryImpl
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.domain.use_case.SearchOpenAndSaveTweetStream
import com.devsparkle.twitterclient.services.MockAddFailRuleService
import com.devsparkle.twitterclient.services.MockDeleteFailRuleService
import com.devsparkle.twitterclient.services.MockRuleService
import com.devsparkle.twitterclient.services.MockTweetService
import com.devsparkle.twitterclient.utils.Constants
import com.devsparkle.twitterclient.utils.MockRemoteRetrofitBuilder
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SearchOpenAndSaveTweetStreamTest {

    lateinit var db: TwitterDatabase
    lateinit var tweetDao: TweetDao
    lateinit var sharedPreferences: SharedPreferences


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, TwitterDatabase::class.java).build()
        tweetDao = db.tweetDao()
        sharedPreferences = context.getSharedPreferences(
            "prefs",
            MODE_PRIVATE
        )
    }

    @Test
    fun `Verify error when no rules are used in the viewmodel`() {
        runTest {
            //=== GIVEN=== /
            // Services
            val retroMock = Retromock.Builder()
                .retrofit(MockRemoteRetrofitBuilder.createRetrofit("https://localhost:8080/"))
                .build()
            val mockRuleService: MockRuleService = retroMock.create(MockRuleService::class.java)
            val mockTweetService: MockTweetService = retroMock.create(MockTweetService::class.java)


            // Repos
            // rule remote repo
            val remoteRuleRepositoryImpl = RemoteRuleRepositoryImpl(mockRuleService)
            val remoteTweetRepositoryImpl = RemoteTweetRepositoryImpl(mockTweetService)
            val localTweetRepositoryImpl = LocalTweetRepositoryImpl(tweetDao, sharedPreferences)

            // viewModel
            val viewModel = SearchOpenAndSaveTweetStream(
                remoteRuleRepositoryImpl,
                remoteTweetRepositoryImpl,
                localTweetRepositoryImpl
            )

            val listRules = emptyList<String>()

            //=== WHEN=== /
            val resource = viewModel(listRules)


            // === THEN
            assertEquals(resource.isAnError(), true)
            assertEquals(
                resource.error()?.message,
                Exception(Constants.EXCEPTION_RULES_NOT_ADDED).message
            )
        }
    }


    @Test
    fun `Verify error when rules could not be deleted`() {
        runTest {
            //=== GIVEN=== /
            // Services
            val retroMock = Retromock.Builder()
                .retrofit(MockRemoteRetrofitBuilder.createRetrofit("https://localhost:8080/"))
                .build()
            val mockRuleService: MockDeleteFailRuleService = retroMock.create(
                MockDeleteFailRuleService::class.java
            )
            val mockTweetService: MockTweetService = retroMock.create(MockTweetService::class.java)

            // Repos
            // rule remote repo
            val remoteRuleRepositoryImpl = RemoteRuleRepositoryImpl(mockRuleService)
            val remoteTweetRepositoryImpl = RemoteTweetRepositoryImpl(mockTweetService)
            val localTweetRepositoryImpl = LocalTweetRepositoryImpl(tweetDao, sharedPreferences)

            // viewModel
            val viewModel = SearchOpenAndSaveTweetStream(
                remoteRuleRepositoryImpl,
                remoteTweetRepositoryImpl,
                localTweetRepositoryImpl
            )
            val listRules = listOf<String>("dogs has:images", "cats has:images")


            //=== WHEN=== /
            val resource = viewModel(listRules)


            //verify(resource.isAnError()).not()

            // === THEN
            assertEquals(resource.isAnError(), true)
            assertEquals(
                resource.error()?.message,
                Exception(Constants.EXCEPTION_RULES_NOT_DELETED).message
            )
        }
    }

    @Test
    fun `Verify error when rules could not be added`() {
        runTest {
            //=== GIVEN=== /
            // Services
            val retroMock = Retromock.Builder()
                .retrofit(MockRemoteRetrofitBuilder.createRetrofit("https://localhost:8080/"))
                .build()
            val mockRuleService: MockAddFailRuleService = retroMock.create(MockAddFailRuleService::class.java)
            val mockTweetService: MockTweetService = retroMock.create(MockTweetService::class.java)


            // Repos
            // rule remote repo
            val remoteRuleRepositoryImpl = RemoteRuleRepositoryImpl(mockRuleService)
            val remoteTweetRepositoryImpl = RemoteTweetRepositoryImpl(mockTweetService)
            val localTweetRepositoryImpl = LocalTweetRepositoryImpl(tweetDao, sharedPreferences)

            // viewModel
            val viewModel = SearchOpenAndSaveTweetStream(
                remoteRuleRepositoryImpl,
                remoteTweetRepositoryImpl,
                localTweetRepositoryImpl
            )
            val listRules = listOf<String>("dogs has:images", "cats has:images")


            //=== WHEN=== /
            val resource = viewModel(listRules)

            // === THEN
            assertEquals(resource.isAnError(), true)
            assertEquals(
                resource.error()?.message,
                Exception(Constants.EXCEPTION_RULES_NOT_ADDED_TO_BACKEND).message
            )
        }
    }

}