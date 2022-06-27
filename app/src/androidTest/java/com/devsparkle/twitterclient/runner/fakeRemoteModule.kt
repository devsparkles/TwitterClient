package com.devsparkle.twitterclient.runner


import android.content.SharedPreferences
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.repository.LocalTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.RemoteRetrofitBuilder
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val TWITTER_RETROFIT = "TWITTER_RETROFIT"
const val SERVER_URL = "SERVER_URL"

val fakeRemoteModule = module {

    factory {
        RemoteTweetRepositoryImpl(
            get<TweetService>()
        ) as RemoteTweetRepository
    }

    factory {
        LocalTweetRepositoryImpl(
            get<TweetDao>(),
            get<SharedPreferences>()
        ) as LocalTweetRepository
    }

    single(named(SERVER_URL)) {
        "http://localhost:8080/"
    }

    single(named(TWITTER_RETROFIT)) {
        RemoteRetrofitBuilder.createRetrofit(androidContext(), get(named(SERVER_URL)))
    }

    factory {
        getTweetService(
            get<Retrofit>(named(TWITTER_RETROFIT))
        )
    }

}

private fun getTweetService(retrofit: Retrofit): TweetService =
    retrofit.create(TweetService::class.java)
