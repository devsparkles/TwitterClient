package com.devsparkle.twitterclient.domain.use_case


import com.devsparkle.twitterclient.base.resource.Resource
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.repository.local.LocalTweetRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteRuleRepository
import com.devsparkle.twitterclient.domain.repository.remote.RemoteTweetRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import okio.BufferedSource
import retrofit2.Call


class GetRemoteTweetStream(
    private val remoteRuleRepository: RemoteRuleRepository,
    private val remoteTweetRepository: RemoteTweetRepository,
    private val localTweetRepository: LocalTweetRepository
) {


    suspend operator fun invoke(query: String): Resource<List<Tweet>> {

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
            rule2Add.add(query)
            remoteRuleRepository.addRule(rule2Add)
            // proceed to open the stream
            val response: Call<ResponseBody>? = remoteTweetRepository.getTweets()

            if (response != null) {
                flow<Tweet> {


                }
            }

//           val  response?.execute()
//            if (tweets.isNotAnError()) {
//                val interval = localTweetRepository.getTweetLifeSpan()
//
//                localTweetRepository.deleteAllTweets()
//                var lastDateInserted = Date()
//                val tweets = tweetsResponse.value()
//                if (tweets != null) {
//                    for (tweet in tweets) {
//                        lastDateInserted = lastDateInserted.addSeconds(interval)
//                        tweet.lifespan = lastDateInserted
//                        localTweetRepository.persistTweet(tweet)
//                    }
//                }
//                return Resource.SuccessWithoutContent()
//            } else {
//                return Resource.Error()
//            }
            return Resource.SuccessWithoutContent()
        } catch (e: Exception) {
            return Resource.Error(e)
        }

    }

//    fun statuses(source: BufferedSource) = flow {
//        val gson = Gson()
//        while (!source.exhausted()) {
//            val s: Status = gson.fromJson(source.readUtf8Line(), Status::class.java)
//            subscriber.onNext(s)
//        }
//    }


}
