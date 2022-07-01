package com.devsparkle.twitterclient.domain.use_case


import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteRuleRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.Constants
import com.devsparkle.twitterclient.utils.LogApp
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retry
import java.util.Date

/***
 * Open the tweet stream and save them inside the local db
 * ---
 * Search tweets by rules,
 * Add rules to the twitter backend
 * Open Tweet Stream
 * Delete all previous tweet in local database
 * Save new tweets in the local database with deletion date interval
 * @param remoteRuleRepository to get delete and add rules for the twitter stream
 * @param remoteTweetRepository to open the twitter stream and get the tweets
 * @param localTweetRepository to save the tweets on the local database
 *
 */
class SearchOpenAndSaveTweetStream(
    private val remoteRuleRepository: RemoteRuleRepository,
    private val remoteTweetRepository: RemoteTweetRepository,
    private val localTweetRepository: LocalTweetRepository
) {

    suspend operator fun invoke(listRules: List<String>): Resource<String> {
        try {
            if (listRules.isEmpty()) {
                return Resource.Error(Exception(Constants.EXCEPTION_RULES_NOT_ADDED))
            }

            // get all current rules
            val rules = remoteRuleRepository.getRules()
            // delete previous rules
            val rulesIds = rules.map { it.id }
            if (rulesIds.isNotEmpty()) {
                val result = remoteRuleRepository.deleteRuleById(rulesIds)
                if (!result) {
                    return Resource.Error(Exception(Constants.EXCEPTION_RULES_NOT_DELETED))
                }
            }

            // add wanted rules
            val rulesToAdd = mutableListOf<String>()
            listRules.forEach {
                rulesToAdd.add(it)
            }
            val result = remoteRuleRepository.addRule(rulesToAdd)
            if (!result) {
                return Resource.Error(Exception(Constants.EXCEPTION_RULES_NOT_ADDED_TO_BACKEND))
            }


            val interval = localTweetRepository.getTweetLifeSpan()
            if (interval == 0 || interval < 0) {
                return Resource.Error(Exception(Constants.EXCEPTION_INTERVAL_ZERO_OR_NEGATIVE))
            }
            localTweetRepository.deleteAllTweets()
            var lastDateInserted = Date()
            remoteTweetRepository.getTweets()
                // the app will buffer 20 result inside the flow and when the buffer is too big just drop the oldest
                .buffer(20, BufferOverflow.DROP_OLDEST)
                // the app will retry to connect to the stream 2 times waiting 1 second
                .retry(2) { e ->
                    LogApp.d(e.message)
                    (e is Exception).also { if (it) delay(1000) }
                }
                .collect {
                    it?.let { t ->
                        //("[id] ${it.id}  [tweet] ${it.text}")
                        lastDateInserted = lastDateInserted.addSeconds(interval)
                        t.lifespan = lastDateInserted
                        localTweetRepository.persistTweet(t)
                    }

                }
            // when the stream is finished
            return Resource.SuccessWithoutContent()
        } catch (e: Exception) {
            return Resource.Error(e)

        }

    }

}
