package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.use_case.DeleteAllTweets
import com.devsparkle.twitterclient.domain.use_case.GetTweetLifeSpan
import com.devsparkle.twitterclient.domain.use_case.SearchTweetsByQuery
import com.devsparkle.twitterclient.domain.use_case.ObserveTweets
import com.devsparkle.twitterclient.domain.use_case.PersistTweetsWithLifeSpan
import com.devsparkle.twitterclient.domain.use_case.PersistTweets
import org.koin.dsl.module

val domainModule = module {

    factory {
        DeleteAllTweets(get())
    }

    factory {
        GetTweetLifeSpan(get())
    }

    factory {
        ObserveTweets(get())
    }

    factory {
        SearchTweetsByQuery(get())
    }

    factory {
        PersistTweets(get())
    }

    factory {
        PersistTweetsWithLifeSpan(get())
    }
}
