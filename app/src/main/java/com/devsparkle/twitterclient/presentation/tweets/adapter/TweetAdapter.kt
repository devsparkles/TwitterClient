package com.devsparkle.twitterclient.presentation.tweets.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devsparkle.twitterclient.R
import com.devsparkle.twitterclient.databinding.ViewHolderTweetItemBinding
import com.devsparkle.twitterclient.domain.model.Tweet


class TweetAdapter(private val clickCallback: ((Tweet) -> Unit)) :
    RecyclerView.Adapter<TweetAdapter.ViewHolder>() {
    private var tweets: List<Tweet> = emptyList()

    fun updateTweets(t: List<Tweet>) {
        this.tweets = t
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewHolderTweetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(clickCallback, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(t = tweets[position])
    }

    override fun getItemCount(): Int = tweets.count()

    class ViewHolder(
        private val clickCallback: (Tweet) -> Unit,
        private val binding: ViewHolderTweetItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(t: Tweet) {


            binding.authorTweet.text = t.text

            binding.root.setOnClickListener { clickCallback.invoke(t) }
        }
    }
}
