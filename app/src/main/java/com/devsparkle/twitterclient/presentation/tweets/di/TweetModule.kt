package com.devsparkle.twitterclient.presentation.tweets.di

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetObservableTweets
import com.devsparkle.twitterclient.domain.use_case.DeleteOldTweet
import com.devsparkle.twitterclient.domain.use_case.SearchAndSaveTweets
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetModule = module {

    viewModel {
        ListTweetViewModel(
            get<DeleteOldTweet>(),
            get<ConfigureTweetLifeSpan>(),
            get<SearchAndSaveTweets>(),
            get<GetObservableTweets>()
        )
    }

    factory { (clickCallback: ((Tweet) -> Unit)) ->
        TweetAdapter(
            clickCallback
        )
    }

}
