package com.devsparkle.twitterclient.data.remote.rule.repository

import com.devsparkle.twitterclient.data.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.rule.dto.DeleteIds
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleToDeleteDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RulesDto
import com.devsparkle.twitterclient.data.remote.rule.service.RuleService
import com.devsparkle.twitterclient.domain.model.Rule
import com.devsparkle.twitterclient.domain.repository.remote.RemoteRuleRepository
import timber.log.Timber

class RemoteRuleRepositoryImpl(val service: RuleService) : RemoteRuleRepository {


    override suspend fun addRule(rule: List<String>): Boolean {
        val list = mutableListOf<RuleDto>()
        rule.forEach {
            list.add(
                RuleDto(
                    value = it, id = null
                )
            )
        }

        try {
            service.addRule(RulesDto(list))
            return true
        } catch (e: Exception) {
            Timber.e(e, "Error adding a rule")
            return false
        }

    }

    override suspend fun deleteRuleById(ids: List<String>): Boolean {
        try {
            service.deleteRule(RuleToDeleteDto(DeleteIds(ids)))
            return true
        } catch (e: Exception) {
            Timber.e(e, "Error deleting a rule")
            return false
        }
    }

    override suspend fun getRules(): List<Rule> {
        try {
            val rules = mutableListOf<Rule>()
            val result = service.listRules()
            result?.data?.forEach {
                rules.add(
                    Rule(
                        id = it.id ?: "",
                        value = it.value ?: ""
                    )
                )
            }
            return result?.data.toDomain()
        } catch (e: Exception) {
            Timber.e(e, "Error deleting a rule")
            return emptyList()
        }
    }
}