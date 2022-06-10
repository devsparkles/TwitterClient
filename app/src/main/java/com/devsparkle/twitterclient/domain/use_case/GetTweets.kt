package com.devsparkle.twitterclient.domain.use_case

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository


class GetTweets(
    private val localTweetRepository: LocalTweetRepository,
    private val remoteTweetRepository: RemoteTweetRepository
) {
    suspend operator fun invoke(query: String,connected: Boolean): Resource<List<Tweet>?> {
        if(connected){
            return remoteTweetRepository.getTweets(query)
        } else {
            return localTweetRepository.getCaseStudies()
        }
    }
}