package com.devsparkle.twitterclient.base.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val baseModule = module {

    factory {
        getCoroutinesDispatchersIo()
    }
}

private fun getCoroutinesDispatchersIo() = Dispatchers.IO
