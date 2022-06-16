package com.devsparkle.twitterclient.presentation.tweets.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.devsparkle.twitterclient.domain.model.Tweet
import com.devsparkle.twitterclient.domain.use_case.DeleteTweet
import com.devsparkle.twitterclient.domain.use_case.GetTweets
import com.devsparkle.twitterclient.utils.extensions.IsFutureDate
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import timber.log.Timber

class DeleteTweetWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params),
    KoinComponent {

    private val deleteTweet: DeleteTweet by inject(DeleteTweet::class.java)
    private val getTweets: GetTweets by inject(GetTweets::class.java)

    override suspend fun doWork(): Result {
        return try {

            val tweets = getTweets.invoke()
            if (tweets.isNotAnError()) {
                tweets.value()?.let { t ->
                    val listOfTweetsToDelete = tweetsToDelete(t)
                    listOfTweetsToDelete.forEach { tweet ->
                        val numberOfDeletedLine = deleteTweet.invoke(tweet)
                        Timber.i("delete tweet ${tweet.id} value $numberOfDeletedLine")
                        Log.d("Worker", "delete tweet ${tweet.id} value $numberOfDeletedLine")
                    }
                }
            }
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }

    private fun tweetsToDelete(list: List<Tweet>): List<Tweet> {
        val result = mutableListOf<Tweet>()
        list.forEach { tweet ->
            tweet.lifespan?.let { lifespan ->
                if (!lifespan.IsFutureDate()) {
                    result.add(tweet)
                }
            }
        }
        return result
    }

    companion object {
        val DELETE_OLD_TWEET = "DELETE_OLD_TWEET"
    }


}
