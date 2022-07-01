package com.devsparkle.twitterclient.data.mapper

import com.devsparkle.twitterclient.data.remote.rule.dto.RuleDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleWrapperDto
import com.devsparkle.twitterclient.domain.model.Rule
import com.devsparkle.twitterclient.domain.model.RuleWrapper


fun RuleDto?.toDomain(): Rule {
    return Rule(
        id = this?.id ?: "",
        value = this?.value ?: ""
    )
}

fun RuleWrapperDto?.toDomain(): RuleWrapper {
    return RuleWrapper(
        data = this?.data?.toDomain()?.toList() ?: emptyList<Rule>(),
    )
}

fun List<RuleDto>?.toDomain(): MutableList<Rule> {
    val result: MutableList<Rule> = mutableListOf()
    this?.forEach { result.add(it.toDomain()) }
    return result
}





