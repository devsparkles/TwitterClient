package com.devsparkle.twitterclient.runner

import android.app.Application
import android.util.Log
import com.devsparkle.twitterclient.data.di.localDataModule
import com.devsparkle.twitterclient.domain.di.domainModule
import com.devsparkle.twitterclient.presentation.tweets.di.tweetModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException

class TwitterTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        try {
            startKoin {
                androidContext(this@TwitterTestApp)
                modules(
                    listOf(
                        // data remote and local module
                        localDataModule,
                        fakeRemoteModule,
                        // dto objects and use cases
                        domainModule,
                        // domain modules
                        tweetModule
                    )
                )
            }

        } catch (koinAlreadyStartedException: KoinAppAlreadyStartedException) {
            Log.i(
                "TwitterTestApp",
                "KoinAppAlreadyStartedException, should only happen in tests"
            )
        }
    }
}