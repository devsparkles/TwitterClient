package com.devsparkle.twitterclient.data.mapper

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleDto
import com.devsparkle.twitterclient.data.remote.rule.dto.RuleWrapperDto
import com.devsparkle.twitterclient.domain.model.Rule
import com.devsparkle.twitterclient.domain.model.RuleWrapper


fun Resource<RuleDto?>.toDomain(): Resource<Rule> {
    return when (this) {
        is Resource.Success -> Resource.Success(this.value().toDomain())
        is Resource.SuccessWithoutContent -> Resource.SuccessWithoutContent()
        is Resource.Error -> Resource.Error(this.error())
        is Resource.Loading -> Resource.Loading()
    }
}

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
//
//fun TweetRuleDto?.tooDomain(): TweetRule {
//    return TweetRule(
//        id = this?.id ?: "",
//        value = this?.value ?: ""
//    )
//}


//fun List<TweetRuleDto>.tooDomain(): List<Rule> {
//    val result: MutableList<Rule> = mutableListOf()
//    this.forEach { result.add(it.toooDomain()) }
//    return result.toList()
//}
//

fun List<RuleDto>?.toDomain(): MutableList<Rule> {
    val result: MutableList<Rule> = mutableListOf()
    this?.forEach { result.add(it.toDomain()) }
    return result
}





