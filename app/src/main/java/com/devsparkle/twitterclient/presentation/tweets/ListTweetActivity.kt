package com.devsparkle.twitterclient.presentation.tweets

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsparkle.twitterclient.R
import com.devsparkle.twitterclient.base.BaseActivity
import com.devsparkle.twitterclient.base.resource.observeResource
import com.devsparkle.twitterclient.databinding.ActivityListTweetBinding
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import com.devsparkle.twitterclient.utils.extensions.hide
import com.devsparkle.twitterclient.utils.extensions.isConnected
import com.devsparkle.twitterclient.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListTweetActivity : BaseActivity() {

    private val TAG: String = "ListTweetActivity"

    //region configuration
    // You can easily configure the tweet lifespan here. 10 is in seconds
    private val TWEET_LIFESPAN = 10

    // Every 5 seconds the app will verify which tweets should be cleaned from the local database
    private var delay = 5000

    // Rules used to define what will Twitter fetch
    val rules = listOf<String>("android kotlin", "cats has:images", "dogs has:images")

    //endregion

    //region injection
    private val viewModel by viewModel<ListTweetViewModel>()
    private val adapter by inject<TweetAdapter> {
        parametersOf(
            { launch: Tweet -> onTapListElementSelected(launch) },
        )
    }
    //endregion

    //region ui
    lateinit var binding: ActivityListTweetBinding
    //endregion

    //region async_code
    private var handler: Handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            com.devsparkle.twitterclient.R.layout.activity_list_tweet
        )
        binding.lifecycleOwner = this
        setupIsConnected()
        setupToolbar()
        setUpRecyclerView()
        setUpResourceObserver()
        viewModel.configureTweetLifespan(TWEET_LIFESPAN)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTweetStream(rules)
    }

    /**
     *  Start the periodic every *delay* call to *deleteOutDatedTweet*
     */
    private fun startRemovingOldTweet() {
        handler
            .postDelayed(Runnable {
                handler.postDelayed(runnable!!, delay.toLong())
                viewModel.deleteOutDatedTweet()
            }.also {
                runnable = it
            },
                delay.toLong()
            )
    }

    /**
     * Stop the periodic call to *deleteOutDatedTweet*
     */
    private fun stopRemovingOldTweet() {
        handler.removeCallbacksAndMessages(null)
    }

    private fun onTapListElementSelected(t: Tweet) = with(binding) {
        showMessage(getString(R.string.detail_tweet, t.text))
    }

    /***
     * If the app is connected it will start the periodic call to remove old tweets.
     * If the app is not connected it will stop that call and save the current already downloaded tweet.
     */
    private fun setupIsConnected() {
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
            if (it) {
                // if the app is connected the process of removing out dated tweet will be started
                startRemovingOldTweet()
            } else {
                // if the app is not connected the process of removing outdated tweets will be stopped.
                stopRemovingOldTweet()
            }
        }
        viewModel.isNetworkAvailable.value = isConnected
    }

    /***
     * Setup the toolbar title
     */
    private fun setupToolbar() = with(binding) {
        toolbar.title = getString(R.string.toolbar_title_default)
    }

    /***
     * Configuration for recyclerview
     */
    private fun setUpRecyclerView() = with(binding) {
        val layoutManager =
            LinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = this@ListTweetActivity.adapter
    }

    /***
     *
     */
    private fun setUpResourceObserver() {
        // when the loading start
        viewModel.remoteTweetState.observeResource(
            this@ListTweetActivity,
            loading = ::onTweetLoading,
            success = ::onTweetySuccess,
            error = ::onTweetError,
            successWithoutContent = {}
        )
        // Everytime a tweet is added to the local database this callback will be fired
        viewModel.observableTweets().observe(this) { displayNewTweets(tweets = it) }
    }


    private fun displayNewTweets(tweets: List<Tweet>?) = with(binding) {
        recyclerview.show()
        loadingScreen.hide()
        emptyDataParent.hide()
        tweets?.let { tweets ->
            if (!isConnected) {
                showNoConnection()
            }
            adapter.updateTweets(tweets)
        } ?: run {
            showMessage("no tweet to show")
        }

    }

    //region tweet_state
    private fun onTweetLoading() = with(binding) {
        loadingScreen.show()
        emptyDataParent.hide()
    }

    private fun showNoConnection() = with(binding) {
        showMessage(getString(R.string.currently_offline_showing_cache_data), Toast.LENGTH_LONG)
        toolbar.title = getString(R.string.toolbar_title_offline)
    }

    private fun onTweetySuccess(value: List<Tweet>?) {
        showMessage("Tweet Stream connection success")
    }

    private fun onTweetError(exception: Exception) = with(binding) {
        loadingScreen.hide()
        toolbar.title = getString(R.string.toolbar_title_error)
        emptyDataParent.show()
        recyclerview.hide()
        showMessage("error:" + exception.message)
    }
    //endregion


}