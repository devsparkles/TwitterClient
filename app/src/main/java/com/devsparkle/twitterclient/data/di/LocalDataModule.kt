package com.devsparkle.twitterclient.data.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.devsparkle.twitterclient.data.local.TwitterDatabase
import com.devsparkle.twitterclient.data.local.tweet.dao.TweetDao
import com.devsparkle.twitterclient.data.local.tweet.repository.LocalTweetRepositoryImpl
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataModule = module {

    single {
        get<TwitterDatabase>().tweetDao()
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            TwitterDatabase::class.java,
            TWITTER_DATABASE
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    factory {
        LocalTweetRepositoryImpl(
            get<TweetDao>(),
            get<SharedPreferences>()
        ) as LocalTweetRepository
    }

    single {
        getSharedPreferences(androidContext())
    }
}
const val TWITTER_DATABASE = "TWITTER_DATABASE"


private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)