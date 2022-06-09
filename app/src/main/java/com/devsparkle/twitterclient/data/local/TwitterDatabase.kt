package com.devsparkle.twitterclient.base.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devsparkle.twitterclient.data.local.casestudy.dao.CaseStudyDao
import com.devsparkle.twitterclient.data.local.casestudy.entities.CaseStudyEntity

/**
 * The Room Database that contains the Case Study table.
 *
 * Note that exportSchema should be true in production databases.
 */
@Database(entities = [CaseStudyEntity::class], version = 2, exportSchema = false)
abstract class TwitterDatabase : RoomDatabase() {

    abstract fun caseStudyDao(): CaseStudyDao
}
