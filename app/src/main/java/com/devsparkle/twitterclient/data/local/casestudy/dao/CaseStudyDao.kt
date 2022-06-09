package com.devsparkle.twitterclient.data.local.casestudy.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devsparkle.twitterclient.data.local.casestudy.entities.CaseStudyEntity

/**
 * Data Access Object for the case study table.
 */
@Dao
interface CaseStudyDao {

    /**
     * Get the list of case studies.
     *
     * @return all case studies.
     */
    @Query("SELECT * FROM casestudies")
    fun getCaseStudies(): List<CaseStudyEntity>


    /**
     * Observes list of case studies.
     *
     * @return all case studies.
     */
    @Query("SELECT * FROM casestudies")
    fun observeCaseStudies(): LiveData<List<CaseStudyEntity>>


    /**
     * Insert a case study in the database. If the case study already exists, replace it.
     *
     * @param casestudy the case study to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaseStudy(casestudy: CaseStudyEntity)


    /**
     * Insert a case studies in the database. If the case study already exists, replace it.
     *
     * @param casestudies the case studies to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCaseStudies(vararg casestudies: CaseStudyEntity)


    /**
     * Delete all cases studies.
     */
    @Query("DELETE FROM casestudies")
    suspend fun deleteCasestudies()


}