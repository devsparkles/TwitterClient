package com.devsparkle.twitterclient.app

import android.app.Application
import android.util.Log
import com.devsparkle.twitterclient.base.di.baseModule
import com.devsparkle.twitterclient.data.di.localDataModule
import com.devsparkle.twitterclient.data.di.remoteDataModule
import com.devsparkle.twitterclient.domain.di.domainModule
import com.devsparkle.twitterclient.presentation.casestudy.di.caseStudyModule
import com.devsparkle.twitterclient.base.di.baseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import timber.log.Timber

class TwitterApp : Application() {


    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }


    private fun setupKoin() {
        try {
            startKoin {
                androidContext(this@TwitterApp)
                modules(
                    listOf(
                        // shared module
                        baseModule,
                        // data remote and local module
                        localDataModule,
                        remoteDataModule,
                        // dto objects and use cases
                        domainModule,
                        // domain modules
                        caseStudyModule

                    )
                )
            }

        } catch (koinAlreadyStartedException: KoinAppAlreadyStartedException) {
            Timber.tag("TwitterApp")
                .i("KoinAppAlreadyStartedException, should only happen in tests")
        }
    }
}
