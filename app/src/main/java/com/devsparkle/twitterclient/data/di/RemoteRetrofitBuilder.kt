package com.devsparkle.twitterclient.data.di

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import java.util.concurrent.TimeUnit
interface RemoteRetrofitBuilder {
    companion object {

        fun createRetrofit(context: Context, serverUrl: String): Retrofit {
            val okHttpClient = createOkHttpClient(context)
            return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun createOkHttpClient(context: Context): OkHttpClient {

            val cacheSize = 10 * 1024 * 1024L // 10 MB

            val cache = Cache(context.cacheDir, cacheSize)

            val timeoutInSeconds = 30L
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .cache(cache)
                .build()
        }

    }
}