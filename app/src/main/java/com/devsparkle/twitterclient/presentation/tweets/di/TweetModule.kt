package com.devsparkle.twitterclient.presentation.tweets.di

import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.DeleteAllTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.SearchTweetsByQuery
import com.devsparkle.twitterclient.domain.use_case.ObserveTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweetsWithLifeSpan
import com.devsparkle.twitterclient.presentation.tweets.adapter.TweetAdapter
import com.devsparkle.twitterclient.presentation.tweets.viewmodel.ListTweetViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tweetModule = module {

    viewModel {
        ListTweetViewModel(
            get<GetTweetLifeSpan>(),
            get<DeleteAllTweets>(),
            get<ObserveTweets>(),
            get<PersistTweetsWithLifeSpan>(),
            get<SearchTweetsByQuery>()
        )
    }

    factory { (clickCallback: ((Tweet) -> Unit)) ->
        TweetAdapter(
            clickCallback
        )
    }

}
