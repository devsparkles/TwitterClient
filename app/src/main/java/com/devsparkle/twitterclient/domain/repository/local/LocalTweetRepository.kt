package com.devsparkle.twitterclient.domain.repository.local

import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import org.w3c.dom.Text

interface LocalTweetRepository {
    suspend fun getCaseStudies():  Resource<List<Tweet>?>
    suspend fun persistCaseStudies(list: List<Tweet>)
}