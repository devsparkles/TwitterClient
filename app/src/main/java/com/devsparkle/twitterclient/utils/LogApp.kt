package com.devsparkle.twitterclient.utils

import timber.log.Timber

object LogApp {

    fun d(text:String?){
        Timber.d(text)
    }
    fun e(text:String?){
        Timber.e(text)
    }
}