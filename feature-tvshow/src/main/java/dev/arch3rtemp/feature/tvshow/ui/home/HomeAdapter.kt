package dev.arch3rtemp.feature.tvshow.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import dev.arch3rtemp.common_ui.base.BaseRVAdapter
import dev.arch3rtemp.feature.tvshow.databinding.TvshowViewLayoutBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.feature.tvshow.ui.util.TvShowComparator

class HomeAdapter(private val clickListener: (TvShowUi) -> Unit)
    : BaseRVAdapter<TvShowUi, TvshowViewLayoutBinding, HomeViewHolder>(TvShowComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemBinding = TvshowViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemBinding, clickListener)
    }
}