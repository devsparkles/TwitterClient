package com.devsparkle.twitterclient.use_cases

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.infinum.retromock.Retromock
import com.devsparkle.twitterclient.data.local.TwitterDatabase
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.repository.LocalTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.rule.repository.RemoteRuleRepositoryImpl
import com.devsparkle.twitterclient.data.remote.rule.service.RuleService
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.domain.use_case.SearchOpenAndSaveTweetStream
import com.devsparkle.twitterclient.services.MockRuleService
import com.devsparkle.twitterclient.services.MockTweetService
import com.devsparkle.twitterclient.utils.Constants
import com.devsparkle.twitterclient.utils.MockRemoteRetrofitBuilder
import io.mockk.every
import io.mockk.mockkStatic
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SearchOpenAndSaveTweetStreamTest {

    lateinit var db: TwitterDatabase
    lateinit var tweetDao: TweetDao
    lateinit var sharedPreferences:SharedPreferences

    @Before
    fun setUp() {
        // get context -- since this is an instrumental test it requires
        // context from the running application
        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        db = Room.inMemoryDatabaseBuilder(context, TwitterDatabase::class.java).build()
        tweetDao = db.tweetDao()
        sharedPreferences = context.getSharedPreferences(
            "prefs",
            MODE_PRIVATE
        );
    }



    @Test
    fun `Verify error when rule could not be added`() {
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
            val localTweetRepositoryImpl = LocalTweetRepositoryImpl(tweetDao,sharedPreferences)

            // viewModel
            val viewModel = SearchOpenAndSaveTweetStream(remoteRuleRepositoryImpl,remoteTweetRepositoryImpl,localTweetRepositoryImpl)

          //   val listRules = listOf<String>("dogs has:images", "cats has:images")


            val listRules = emptyList<String>()

            //=== WHEN=== /
           val resource =   viewModel.invoke(listRules)


            // === THEN
            assertEquals(resource.isAnError(), true)
            assertEquals(resource.error()?.message, Exception(Constants.EXCEPTION_NO_RULES_ADDED).message)
        }
    }

}