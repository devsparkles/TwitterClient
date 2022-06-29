package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteRuleRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import java.util.*


class AddNewRules(
    private val remoteRuleRepository: RemoteRuleRepository
) {

    suspend operator fun invoke(listRules: List<String>): Resource<Boolean> {

        try {
            // get all current rules
            val rules = remoteRuleRepository.getRules()

            // delete previous rules
            val rulesIds = rules.map { it.id }
            if (rulesIds.isNotEmpty()) {
                remoteRuleRepository.deleteRuleById(rulesIds)
            }

            // add wanted rules
            val rule2Add = mutableListOf<String>()
            listRules.forEach {
                rule2Add.add(it)
            }
            remoteRuleRepository.addRule(rule2Add)

            return Resource.SuccessWithoutContent()
        } catch (e: Exception) {
            return Resource.Error(e)

        }

    }


}