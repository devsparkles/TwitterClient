package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.use_case.*
import org.koin.dsl.module

val domainModule = module {

    factory {
        SearchOpenAndSaveTweetStream(get(), get(), get())
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
