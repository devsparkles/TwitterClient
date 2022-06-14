package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.domain.use_case.DeleteAllTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.ObserveTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweet
import com.devsparkle.twitterclient.domain.use_case.PersistTweets
import org.koin.dsl.module

val domainModule = module {

    factory {
        DeleteAllTweets(
            get<LocalTweetRepository>()
        )
    }

    factory {
        GetTweetLifeSpan(
            get<LocalTweetRepository>()
        )
    }

    factory {
        ObserveTweets(
            get<LocalTweetRepository>()
        )
    }

    factory {
        GetTweets(
            get<RemoteTweetRepository>()
        )
    }

    factory {
        PersistTweets(
            get<LocalTweetRepository>()
        )
    }

    factory {
        PersistTweet(
            get<LocalTweetRepository>()
        )
    }
}
