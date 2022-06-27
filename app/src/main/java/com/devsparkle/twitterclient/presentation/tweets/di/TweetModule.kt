package com.devsparkle.twitterclient.presentation.tweets.di

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetLocalTweets
import com.devsparkle.twitterclient.domain.use_case.DeleteLocalOldTweet
import com.devsparkle.twitterclient.domain.use_case.GetRemoteTweetStream
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetModule = module {

    viewModel {
        ListTweetViewModel(
            get<DeleteLocalOldTweet>(),
            get<ConfigureTweetLifeSpan>(),
            get<GetRemoteTweetStream>(),
            get<GetLocalTweets>()
        )
    }

    factory { (clickCallback: ((Tweet) -> Unit)) ->
        TweetAdapter(
            clickCallback
        )
    }

}
