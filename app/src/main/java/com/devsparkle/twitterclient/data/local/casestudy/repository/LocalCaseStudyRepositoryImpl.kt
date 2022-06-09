package com.devsparkle.twitterclient.data.local.casestudy.repository

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.local.casestudy.dao.CaseStudyDao
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toDomainCaseStudy
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toEntity
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.repository.local.LocalCaseStudyRepository

class LocalCaseStudyRepositoryImpl(private val caseStudyDao: CaseStudyDao) :
    LocalCaseStudyRepository {

    override suspend fun getCaseStudies(): Resource<List<CaseStudy>?> {
        return Resource.of<List<CaseStudy>?> {
            caseStudyDao.getCaseStudies().toDomainCaseStudy()
        }
    }

    override suspend fun persistCaseStudies(list: List<CaseStudy>) {
        caseStudyDao.deleteCasestudies()
        list.forEach {
            caseStudyDao.insertCaseStudy(it.toEntity())
        }
    }
}

