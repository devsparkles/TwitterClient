package com.devsparkle.twitterclient.data.remote

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

interface RemoteRetrofitBuilder {
    companion object {

        fun createRetrofit(context: Context, serverUrl: String): Retrofit {
            val okHttpClient = createOkHttpClient()
            return Retrofit.Builder()
                .baseUrl(serverUrl)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        private fun createOkHttpClient(): OkHttpClient {

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return OkHttpClient.Builder()
                .writeTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(0, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(OAuthInterceptor("Bearer", "AAAAAAAAAAAAAAAAAAAAALcSdgEAAAAAW8S0SWmcUaiQoM4%2F0Q%2FWWJ7Hoyk%3DydCCizNCgGsExYExd8XL9QUNzOCB3G920AVYayotiinWYzyOP6"))
                .build()
        }


    }
}