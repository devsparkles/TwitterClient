package com.devsparkle.twitterclient.data.remote.casestudy.repository

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.remote.casestudy.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.casestudy.service.CaseStudyService
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.repository.remote.RemoteCaseStudyRepository
import timber.log.Timber

class RemoteCaseStudyRepositoryImpl(private val service: CaseStudyService) :
    RemoteCaseStudyRepository {

    override suspend fun getCaseStudies(): Resource<List<CaseStudy>?> {
        try {
            val result = service.getCaseStudies()
            return Resource.of {
                result.toDomain().casestudies
            }
        } catch (e: Exception) {
            Timber.e(e, "Error fetching case studies")
        }
        return Resource.Error()
    }
}