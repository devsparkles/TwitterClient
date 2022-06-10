package com.devsparkle.twitterclient.domain.repository.remote

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet

interface RemoteTweetRepository {
    suspend fun getTweets(query: String): Resource<List<Tweet>?>
}