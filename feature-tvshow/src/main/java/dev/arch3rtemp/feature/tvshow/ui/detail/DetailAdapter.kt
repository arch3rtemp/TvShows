package dev.arch3rtemp.feature.tvshow.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.arch3rtemp.common_ui.base.BaseRVAdapter
import dev.arch3rtemp.feature.tvshow.databinding.SimilarViewLayoutBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.feature.tvshow.ui.util.TvShowComparator

class DetailAdapter(private val clickListener: (TvShowUi) -> Unit)
    : BaseRVAdapter<TvShowUi, SimilarViewLayoutBinding, DetailViewHolder>(TvShowComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val itemBinding = SimilarViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailViewHolder(itemBinding, clickListener)
    }
}