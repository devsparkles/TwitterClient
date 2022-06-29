package com.devsparkle.twitterclient.domain.repository.remote

import com.devsparkle.twitterclient.domain.model.Tweet
import kotlinx.coroutines.flow.Flow

interface RemoteTweetRepository {
    suspend fun getTweets(): Flow<Tweet?>
}