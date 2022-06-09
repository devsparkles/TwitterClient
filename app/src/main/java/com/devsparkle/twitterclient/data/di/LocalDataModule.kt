package com.devsparkle.twitterclient.data.di

import android.content.Context
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.devsparkle.twitterclient.base.local.TwitterDatabase
import com.devsparkle.twitterclient.data.local.casestudy.dao.CaseStudyDao
import com.devsparkle.twitterclient.data.local.casestudy.repository.LocalCaseStudyRepositoryImpl
import com.devsparkle.twitterclient.data.remote.casestudy.repository.RemoteCaseStudyRepositoryImpl
import com.devsparkle.twitterclient.data.remote.casestudy.service.CaseStudyService
import com.devsparkle.twitterclient.domain.repository.local.LocalCaseStudyRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteCaseStudyRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val localDataModule = module {

    single {
        get<TwitterDatabase>().caseStudyDao()
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
        LocalCaseStudyRepositoryImpl(
            get<CaseStudyDao>()
        ) as LocalCaseStudyRepository
    }

    single {
        getSharedPreferences(androidContext())
    }
}
const val TWITTER_DATABASE = "TWITTER_DATABASE"


private fun getSharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)