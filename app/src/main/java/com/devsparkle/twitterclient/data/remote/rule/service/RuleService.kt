package com.devsparkle.twitterclient.data.remote.rule.service

import com.devsparkle.twitterclient.data.remote.rule.dto.RuleWrapperDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleToDeleteDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RulesDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RuleService {

    @GET("/2/tweets/search/stream/rules")
    suspend fun listRules(): RuleWrapperDto?

    @POST("/2/tweets/search/stream/rules")
    suspend fun addRule(@Body rules: RulesDto)

    @POST("/2/tweets/search/stream/rules")
    suspend fun deleteRule(@Body deleteRules: RuleToDeleteDto): RuleWrapperDto?

}