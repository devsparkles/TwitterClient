package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.use_case.ConfigureTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.DeleteTweet
import com.devsparkle.twitterclient.domain.use_case.GetObservableTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.domain.use_case.SearchAndSaveTweets
import org.koin.dsl.module

val domainModule = module {

    factory {
        SearchAndSaveTweets(get(), get())
    }

    factory {
        ConfigureTweetLifeSpan(get())
    }

    factory {
        GetObservableTweets(get())
    }

    factory {
        DeleteTweet(get())
    }

    factory {
        GetTweets(get())
    }

}
