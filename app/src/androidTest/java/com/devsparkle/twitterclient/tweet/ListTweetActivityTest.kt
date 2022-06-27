package com.devsparkle.twitterclient.tweet

import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.devsparkle.twitterclient.R
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.presentation.tweets.ListTweetActivity
import com.devsparkle.twitterclient.util.FileReader
import com.devsparkle.twitterclient.utils.EspressoIdlingResource
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject

@LargeTest
@RunWith(AndroidJUnit4::class)
class ListTweetActivityTest : KoinTest {

    private val mockWebServer = MockWebServer()
    private val tweetDao by inject<TweetDao>()

    @Before
    fun init() {
        runBlocking {
            tweetDao.deleteTweets()
        }
    }


    @Before
    fun setup() {
        mockWebServer.start(8080)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }



    @Test
    fun listTweetActivityTest2() {
        //Given
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return MockResponse()
                    .setResponseCode(200)
                    .setBody(FileReader.readTestFile("twotweets.json"))
            }
        }

        // When
        val activityScenario = ActivityScenario.launch(ListTweetActivity::class.java)
        val appCompatEditText = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.et_query),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.query_box),
                        childAtPosition(
                            ViewMatchers.withId(R.id.constraint_layout_main),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(
            ViewActions.replaceText("voiture"),
            ViewActions.closeSoftKeyboard()
        )

        val materialButton = onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.btn_search), withText("Search"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.query_box),
                        childAtPosition(
                            ViewMatchers.withId(R.id.constraint_layout_main),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(ViewActions.click())

        Thread.sleep(10000)
        onView(withText("tweet1")).check(ViewAssertions.matches(isDisplayed()))
        onView(withText("tweet2")).check(ViewAssertions.matches(isDisplayed()))

        activityScenario.close()

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }



}


