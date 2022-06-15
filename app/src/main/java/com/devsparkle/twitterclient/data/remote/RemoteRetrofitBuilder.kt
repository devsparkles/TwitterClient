package com.devsparkle.twitterclient.data.remote

import android.content.Context
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
            val timeoutInSeconds = 30L
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .connectTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .readTimeout(timeoutInSeconds, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(OAuthInterceptor("Bearer", "AAAAAAAAAAAAAAAAAAAAALcSdgEAAAAAW8S0SWmcUaiQoM4%2F0Q%2FWWJ7Hoyk%3DydCCizNCgGsExYExd8XL9QUNzOCB3G920AVYayotiinWYzyOP6"))
                .build()
        }


    }
}