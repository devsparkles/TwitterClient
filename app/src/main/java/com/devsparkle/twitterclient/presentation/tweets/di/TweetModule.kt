package com.devsparkle.twitterclient.presentation.tweets.di

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.*
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetModule = module {

    viewModel {
        ListTweetViewModel(
            get<DeleteLocalOldTweet>(),
            get<ConfigureTweetLifeSpan>(),
            get<SearchOpenAndSaveTweetStream>(),
            get<GetLocalTweets>()
        )
    }

    factory { (clickCallback: ((Tweet) -> Unit)) ->
        TweetAdapter(
            clickCallback
        )
    }

}
