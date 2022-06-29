package com.devsparkle.twitterclient.data.remote.tweet.repository

import com.devsparkle.twitterclient.data.remote.tweet.service.TweetService
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import timber.log.Timber

class RemoteTweetRepositoryImpl(private val service: TweetService) :
    RemoteTweetRepository {

    override suspend fun getTweets(): Observable<ResponseBody> {
        try {
            return service.getTweets()
        } catch (e: Exception) {
            Timber.e(e, "Error fetching tweets")
            return Observable.just(null)
        }
    }

}