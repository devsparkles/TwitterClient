package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweets
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetTweets(
            get<LocalTweetRepository>(),
            get<RemoteTweetRepository>()
        )
    }


    factory {
        PersistTweets(
            get<LocalTweetRepository>()
        )
    }



}
