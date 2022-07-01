package com.devsparkle.twitterclient

import com.devsparkle.twitterclient.data.remote.OAuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface MockRemoteRetrofitBuilder {
    companion object {

        fun createRetrofit(serverUrl: String): Retrofit {
            val okHttpClient = createDefaultOkHttpClient()
            return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

       private fun createDefaultOkHttpClient(): OkHttpClient {
            val globalTimeout = 30L
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .writeTimeout(globalTimeout, TimeUnit.SECONDS)
                .connectTimeout(globalTimeout, TimeUnit.SECONDS)
                .readTimeout(globalTimeout, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(OAuthInterceptor("Bearer", "randomKey"))
                .build()
        }


        fun createLenientRetrofit(serverUrl: String): Retrofit {
            val okHttpClient = createNoTimeoutOkHttpClient()
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        private fun createNoTimeoutOkHttpClient(): OkHttpClient {
            return OkHttpClient().newBuilder()
                .addInterceptor(OAuthInterceptor("Bearer", "randomKey"))
                .build()
        }

    }
}