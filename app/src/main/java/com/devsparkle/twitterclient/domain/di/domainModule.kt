package com.devsparkle.twitterclient.domain.di

import com.devsparkle.twitterclient.domain.repository.local.LocalCaseStudyRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteCaseStudyRepository
import com.devsparkle.twitterclient.domain.use_case.GetCaseStudies
import com.devsparkle.twitterclient.domain.use_case.PersistCaseStudies
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetCaseStudies(
            get<LocalCaseStudyRepository>(),
            get<RemoteCaseStudyRepository>()
        )
    }


    factory {
        PersistCaseStudies(
            get<LocalCaseStudyRepository>()
        )
    }



}
