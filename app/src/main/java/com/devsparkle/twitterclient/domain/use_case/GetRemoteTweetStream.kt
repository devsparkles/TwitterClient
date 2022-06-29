package com.devsparkle.twitterclient.domain.use_case


import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import java.util.Date


class GetRemoteTweetStream(
    private val remoteTweetRepository: RemoteTweetRepository,
    private val localTweetRepository: LocalTweetRepository
) {

    suspend operator fun invoke(): Resource<Boolean> {
        try {
            val interval = localTweetRepository.getTweetLifeSpan()
            localTweetRepository.deleteAllTweets()
            var lastDateInserted = Date()
            remoteTweetRepository.getTweets().buffer(20).collect {
                it?.let { t ->
                    println("[id] ${it.id}  [tweet] ${it.text}")
                    lastDateInserted = lastDateInserted.addSeconds(interval)
                    t.lifespan = lastDateInserted
                    localTweetRepository.persistTweet(t)
                }

            }
            return Resource.SuccessWithoutContent()
        } catch (e: Exception) {
            return Resource.Error(e)

        }

    }

}
