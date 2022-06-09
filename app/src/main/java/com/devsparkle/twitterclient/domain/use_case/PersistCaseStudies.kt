package com.devsparkle.twitterclient.domain.use_case


import com.devsparkle.twitterclient.domain.model.CaseStudy
import com.devsparkle.twitterclient.domain.repository.local.LocalCaseStudyRepository


class PersistCaseStudies(
    private val localCaseStudyRepository: LocalCaseStudyRepository
) {
    suspend operator fun invoke(list: List<CaseStudy>) {
        localCaseStudyRepository.persistCaseStudies(list)
    }
}
