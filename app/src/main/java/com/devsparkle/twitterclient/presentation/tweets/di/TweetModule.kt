package com.devsparkle.twitterclient.presentation.tweets.di

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweets
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetModule = module {

    viewModel {
        ListTweetViewModel(
            get<PersistTweets>(),
            get<GetTweets>(),
            get<CoroutineDispatcher>()
        )
    }

    factory { (clickCallback: ((Tweet) -> Unit)) ->
        TweetAdapter(
            clickCallback
        )
    }

}
