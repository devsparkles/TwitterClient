package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.model.CaseStudyWrapper
import com.devsparkle.twitterclient.domain.repository.local.LocalCaseStudyRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteCaseStudyRepository


class GetCaseStudies(
    private val localCaseStudyRepository: LocalCaseStudyRepository,
    private val remoteCaseStudyRepository: RemoteCaseStudyRepository
) {
    suspend operator fun invoke(connected: Boolean): Resource<List<CaseStudy>?> {
        if(connected){
            return remoteCaseStudyRepository.getCaseStudies()
        } else {
            return localCaseStudyRepository.getCaseStudies()
        }
    }
}