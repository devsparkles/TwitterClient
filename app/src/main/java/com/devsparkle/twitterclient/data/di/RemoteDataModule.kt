package com.devsparkle.twitterclient.data.di


import com.devsparkle.twitterclient.data.remote.casestudy.repository.RemoteCaseStudyRepositoryImpl
import com.devsparkle.twitterclient.data.remote.casestudy.service.CaseStudyService
import com.devsparkle.twitterclient.domain.repository.remote.RemoteCaseStudyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit


const val TWITTER_RETROFIT = "KINCARTA_RETROFIT"
const val SERVER_URL = "SERVER_URL"

val remoteDataModule = module {


    single(named(SERVER_URL)) {
        //BuildConfig.API_URL
        "https://raw.githubusercontent.com/"
    }

    single(named(TWITTER_RETROFIT)) {
        RemoteRetrofitBuilder.createRetrofit(androidContext(), get<String>(named(SERVER_URL)))
    }


    factory {
        RemoteCaseStudyRepositoryImpl(
            get<CaseStudyService>()
        ) as RemoteCaseStudyRepository
    }


    factory {
        getCaseStudyService(
            get<Retrofit>(named(TWITTER_RETROFIT))
        )
    }

}

private fun getCaseStudyService(retrofit: Retrofit): CaseStudyService =
    retrofit.create(CaseStudyService::class.java)

