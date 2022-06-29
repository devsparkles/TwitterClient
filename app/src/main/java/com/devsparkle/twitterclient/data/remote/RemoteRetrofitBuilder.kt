package com.devsparkle.twitterclient.data.remote

import com.devsparkle.twitterclient.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface RemoteRetrofitBuilder {
    companion object {

        fun createRetrofit(serverUrl: String): Retrofit {
            val okHttpClient = createDefaultOkHttpClient()
            return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun createDefaultOkHttpClient(): OkHttpClient {
            val globalTimeout = 30L
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .writeTimeout(globalTimeout, TimeUnit.SECONDS)
                .connectTimeout(globalTimeout, TimeUnit.SECONDS)
                .readTimeout(globalTimeout, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(OAuthInterceptor("Bearer", Constants.TWITTER_API_KEY))
                .build()
        }

        fun createNoTimeoutOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                .addInterceptor(OAuthInterceptor("Bearer", Constants.TWITTER_API_KEY))
                .build()
        }

    }
}