package com.devsparkle.twitterclient.domain.repository.remote

import com.devsparkle.twitterclient.domain.model.Rule

interface RemoteRuleRepository {
    suspend fun addRule(rule: List<String>): Boolean
    suspend fun deleteRuleById(ids: List<String>): Boolean
    suspend fun getRules(): List<Rule>
}