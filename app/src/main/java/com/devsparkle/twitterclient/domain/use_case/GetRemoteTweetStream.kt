package com.devsparkle.twitterclient.domain.use_case


import android.util.Log
import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.data.mapper.toDomain
import com.devsparkle.twitterclient.data.remote.OAuthInterceptor
import com.devsparkle.twitterclient.data.remote.tweet.dto.TweetWrapperDto
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.devsparkle.twitterclient.utils.extensions.addSeconds
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.*


class GetRemoteTweetStream(
    private val remoteTweetRepository: RemoteTweetRepository,
    private val localTweetRepository: LocalTweetRepository
) {

    suspend operator fun invoke(): Resource<Boolean> {
        try {
            val interval = localTweetRepository.getTweetLifeSpan()
            localTweetRepository.deleteAllTweets()
            var lastDateInserted = Date()

                //  remoteTweetRepository.getTweets()

            val client: OkHttpClient = OkHttpClient()
                .newBuilder()
                .addInterceptor(OAuthInterceptor("Bearer", "AAAAAAAAAAAAAAAAAAAAALcSdgEAAAAAW8S0SWmcUaiQoM4%2F0Q%2FWWJ7Hoyk%3DydCCizNCgGsExYExd8XL9QUNzOCB3G920AVYayotiinWYzyOP6"))
                .build()
            val request: Request = Request.Builder()
                .url("https://api.twitter.com/2/tweets/search/stream")
                .method("GET", null)
                .build()
            val response: Response = client.newCall(request).execute()
            val source = response.body?.source()
            val buffer = Buffer()
            while(!source!!.exhausted()){
                response.body?.source()?.read(buffer, 8192)
                val data = buffer.readString(Charset.defaultCharset())
                println("yo" + data)
            }



            // proceed to open the stream
//            remoteTweetRepository.getTweets()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    it?.let {
//                        while(!it.source().exhausted()){
//                            println("yo" + it.source().readUtf8Line())
//                        }
//                    }
//                },{
//                    println("yoerror" + it.message.toString())
//                })

//            remoteTweetRepository.getLine().buffer(50).collect {
//                Log.d("line", it)
//                val tweet: TweetWrapperDto =
//                    Gson().fromJson(it, TweetWrapperDto::class.java)
//
//                tweet.data.toDomain().let { t ->
//                    lastDateInserted = lastDateInserted.addSeconds(interval)
//                    t.lifespan = lastDateInserted
//                    localTweetRepository.persistTweet(t)
//                }
//            }
            return Resource.SuccessWithoutContent()
        } catch (e: Exception) {
            return Resource.Error(e)

        }

    }

}
