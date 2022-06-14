package com.devsparkle.twitterclient.presentation.tweets

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.devsparkle.twitterclient.R
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

class ListTweetActivity : AppCompatActivity() {

    private val TAG: String = "ListTweetActivity"

    lateinit var binding: ActivityListTweetBinding
    private val viewModel by viewModel<ListTweetViewModel>()
    private val adapter by inject<TweetAdapter> {
        parametersOf(
            { launch: Tweet -> onTapListElementSelected(launch) },
        )
    }

    private fun onTapListElementSelected(cs: Tweet) = with(binding) {
        Toast.makeText(root.context, "detail tweet ${cs.id}", Toast.LENGTH_SHORT)
            .show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(
                this,
                com.devsparkle.twitterclient.R.layout.activity_list_tweet
            )
        binding.lifecycleOwner = this
        setupToolbar()
        setUpRecyclerView()
        setupButton()
        setUpResourceObserver()
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

    private fun setupButton() =  with(binding) {
        btnSearch.setOnClickListener() {
            if(etQuery.text.toString().isNotEmpty()){
                viewModel.searchTweetsByQuery(etQuery.text.toString(), isConnected = isConnected)
            } else {
                showMessage("Can't search empty text")
            }
        }
    }

    private fun setUpResourceObserver() {
        viewModel.observeTweetFromLocal().observe(
            this@ListTweetActivity
        ) {
            onTweetySuccess(it)
        }
    }


    private fun onTweetLoading() = with(binding) {
        loadingScreen.show()
        emptyDataParent.hide()
    }

    private fun onTweetySuccess(value: List<Tweet>?) = with(binding) {
        hideKeyboard()
        recyclerview.show()
        loadingScreen.hide()
        emptyDataParent.hide()
        value?.let {
            it.let { tweets ->
                if (!isConnected) {
                    showMessage(getString(R.string.currently_offline_showing_cache_data), Toast.LENGTH_LONG)
                    toolbar.title = getString(R.string.toolbar_title_offline)
                }
                adapter.updateTweets(tweets)
                if (tweets.isEmpty()) {
                    emptyDataParent.visibility = View.VISIBLE
                }
            }
        } ?: run {
            showMessage("no tweet to show")
        }

    }

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