package com.devsparkle.twitterclient.domain.repository.local

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.CaseStudy

interface LocalCaseStudyRepository {
    suspend fun getCaseStudies():  Resource<List<CaseStudy>?>
    suspend fun persistCaseStudies(list: List<CaseStudy>)
}