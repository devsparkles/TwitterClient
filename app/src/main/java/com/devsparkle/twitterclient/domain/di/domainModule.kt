package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.GetLocalTweets
import com.devsparkle.twitterclient.domain.use_case.DeleteLocalOldTweet
import com.devsparkle.twitterclient.domain.use_case.GetRemoteTweetStream
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetRemoteTweetStream(get(), get(), get())
    }

    factory {
        ConfigureTweetLifeSpan(get())
    }

    factory {
        GetLocalTweets(get())
    }
    factory {
        DeleteLocalOldTweet(get())
    }
}
