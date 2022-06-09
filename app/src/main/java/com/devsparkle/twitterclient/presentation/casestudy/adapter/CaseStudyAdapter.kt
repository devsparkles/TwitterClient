package com.devsparkle.twitterclient.presentation.casestudy.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.devsparkle.twitterclient.R
import com.devsparkle.twitterclient.databinding.ViewHolderCaseStudyBinding
import com.devsparkle.twitterclient.domain.model.CaseStudy


class CaseStudyAdapter(private val clickCallback: ((CaseStudy) -> Unit)) :
    RecyclerView.Adapter<CaseStudyAdapter.ViewHolder>() {
    private var casestudies: List<CaseStudy> = emptyList()

    fun updateCaseStudies(cs: List<CaseStudy>) {
        this.casestudies = cs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewHolderCaseStudyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(clickCallback, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(caseStudy = casestudies[position])
    }

    override fun getItemCount(): Int = casestudies.count()

    class ViewHolder(
        private val clickCallback: (CaseStudy) -> Unit,
        private val binding: ViewHolderCaseStudyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(caseStudy: CaseStudy) {

            Glide.with(binding.root.context)
                .load(caseStudy.heroImageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.heroImage)


            binding.teaser.text = caseStudy.teaser
            binding.root.setOnClickListener { clickCallback.invoke(caseStudy) }
        }
    }
}
