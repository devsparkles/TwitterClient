package com.devsparkle.twitterclient.data.remote.casestudy.service

import com.devsparkle.twitterclient.data.remote.casestudy.dto.CaseStudyWrapperDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CaseStudyService {

    @GET("/theappbusiness/engineering-challenge/main/endpoints/v1/caseStudies.json")
    suspend fun getCaseStudies(): CaseStudyWrapperDto?

}