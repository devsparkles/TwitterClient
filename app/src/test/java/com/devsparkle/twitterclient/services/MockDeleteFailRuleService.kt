package com.devsparkle.twitterclient.services

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleToDeleteDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleWrapperDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RulesDto
import com.devsparkle.twitterclient.data.remote.rule.service.RuleService
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MockDeleteFailRuleService : RuleService {

    @Mock
    @GET("/2/tweets/search/stream/rules")
    @MockResponse(body = "{\"data\":[{\"id\":\"1542837900454371330\",\"value\":\"dogs has:images\"},{\"id\":\"1542837900454371331\",\"value\":\"cats has:images\"}],\"meta\":{\"sent\":\"2022-07-01T11:55:50.782Z\",\"result_count\":2}}")
    override suspend fun listRules(): RuleWrapperDto?

    @Mock
    @POST("/2/tweets/search/stream/rules")
    @MockResponse(body = "{\"data\":[{\"value\":\"dogs has:images\",\"id\":\"1542839925128798209\"},{\"value\":\"cats has:images\",\"id\":\"1542839925128798208\"}],\"meta\":{\"sent\":\"2022-07-01T11:57:58.470Z\",\"summary\":{\"created\":2,\"not_created\":0,\"valid\":2,\"invalid\":0}}}")
    override suspend fun addRule(@Body rules: RulesDto)

    @Mock
    @POST("/2/tweets/search/stream/rules")
    @MockResponse(code = 400)
    override suspend fun deleteRule(@Body deleteRules: RuleToDeleteDto): RuleWrapperDto?

}