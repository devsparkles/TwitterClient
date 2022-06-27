package com.devsparkle.twitterclient.data.di


import com.devsparkle.twitterclient.BuildConfig
import com.devsparkle.twitterclient.data.remote.RemoteRetrofitBuilder
import com.devsparkle.twitterclient.data.remote.rule.repository.RemoteRuleRepositoryImpl
import com.devsparkle.twitterclient.data.remote.rule.service.RuleService
import com.devsparkle.twitterclient.data.remote.tweet.repository.RemoteTweetRepositoryImpl
import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.devsparkle.twitterclient.domain.repository.remote.RemoteRuleRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val TWITTER_RETROFIT = "TWITTER_RETROFIT"
const val SERVER_URL = "SERVER_URL"

val remoteDataModule = module {


    single(named(SERVER_URL)) {
        BuildConfig.API_URL
       //"https://raw.githubusercontent.com/"
    }

    single(named(TWITTER_RETROFIT)) {
        RemoteRetrofitBuilder.createRetrofit(androidContext(), get<String>(named(SERVER_URL)))
    }

    factory {
        RemoteRuleRepositoryImpl(
            get<RuleService>()
        ) as RemoteRuleRepository
    }

    factory {
        RemoteTweetRepositoryImpl(
            get<TweetService>()
        ) as RemoteTweetRepository
    }

    factory {
        getRuleService(
            get<Retrofit>(named(TWITTER_RETROFIT))
        )
    }

    factory {
        getCaseStudyService(
            get<Retrofit>(named(TWITTER_RETROFIT))
        )
    }

}

private fun getRuleService(retrofit: Retrofit): RuleService =
    retrofit.create(RuleService::class.java)

private fun getCaseStudyService(retrofit: Retrofit): TweetService =
    retrofit.create(TweetService::class.java)

