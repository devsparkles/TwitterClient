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
import com.devsparkle.twitterclient.utils.extensions.hideKeyboard
import com.devsparkle.twitterclient.utils.extensions.isConnected
import com.devsparkle.twitterclient.utils.extensions.show
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ListTweetActivity : BaseActivity() {

    private val TAG: String = "ListTweetActivity"


    lateinit var binding: ActivityListTweetBinding
    private val viewModel by viewModel<ListTweetViewModel>()
    private val adapter by inject<TweetAdapter> {
        parametersOf(
            { launch: Tweet -> onTapListElementSelected(launch) },
        )
    }

    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable: Runnable? = null
    var delay = 5000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                com.devsparkle.twitterclient.R.layout.activity_list_tweet
            )
        binding.lifecycleOwner = this
        setupIsConnected()
        setupToolbar()
        setUpRecyclerView()
        setupButton()
        setUpResourceObserver()
        // You can easily configure the tweet lifespan here. 10 is in seconds
        viewModel.configureTweetLifespan(9)
    }

    override fun onResume() {
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            viewModel.deleteOutDatedTweet()
        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }

    private fun onTapListElementSelected(cs: Tweet) = with(binding) {
        Toast.makeText(root.context, "detail tweet ${cs.id}", Toast.LENGTH_SHORT)
            .show()
    }



    private fun setupIsConnected() {
        connectionLiveData.observe(this) {
            viewModel.isNetworkAvailable.value = it
        }
        viewModel.isNetworkAvailable.value = isConnected
    }


    private fun setupToolbar() = with(binding) {
        toolbar.title = getString(R.string.toolbar_title_default)
    }

    private fun setUpRecyclerView() = with(binding) {
        val layoutManager =
            LinearLayoutManager(recyclerview.context, LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = this@ListTweetActivity.adapter
    }

    private fun setupButton() = with(binding) {
        btnSearch.setOnClickListener {
            if (isConnected && etQuery.text.toString().isNotEmpty()) {
                viewModel.searchTweetsByQuery(etQuery.text.toString())
            } else if (etQuery.text.toString().isNotEmpty()) {
                showMessage("Can't search empty text")
            } else if (!isConnected) {
                showMessage("Can't search without connectivity")
            }
        }
    }

    private fun setUpResourceObserver() {
        viewModel.remoteTweetState.observeResource(
            this@ListTweetActivity,
            loading = ::onTweetLoading,
            success = ::onTweetySuccess,
            error = ::onTweetError,
            successWithoutContent = {}
        )
        viewModel.observableTweets().observe(this) { displayNewTweets(tweets = it) }
    }


    private fun onTweetLoading() = with(binding) {
        loadingScreen.show()
        emptyDataParent.hide()
    }

    private fun displayNewTweets(tweets: List<Tweet>?) = with(binding) {
        hideKeyboard()
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

    private fun showNoConnection() = with(binding) {
        showMessage(
            getString(R.string.currently_offline_showing_cache_data),
            Toast.LENGTH_LONG
        )
        toolbar.title = getString(R.string.toolbar_title_offline)
    }

    private fun onTweetySuccess(value: List<Tweet>?) { }

    private fun onTweetError(exception: Exception) = with(binding) {
        loadingScreen.hide()
        toolbar.title = getString(R.string.toolbar_title_error)
        emptyDataParent.show()
        recyclerview.hide()
        showMessage("error:" + exception.message)
    }


    private fun showMessage(message: String, duration: Int = Toast.LENGTH_SHORT) = with(binding) {
        Toast.makeText(root.context, message, duration).show()
    }
}