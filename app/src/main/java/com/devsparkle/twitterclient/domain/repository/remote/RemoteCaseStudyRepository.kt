package com.devsparkle.twitterclient.domain.repository.remote

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.model.CaseStudyWrapper

interface RemoteCaseStudyRepository {
    suspend fun getCaseStudies(): Resource<List<CaseStudy>?>
}