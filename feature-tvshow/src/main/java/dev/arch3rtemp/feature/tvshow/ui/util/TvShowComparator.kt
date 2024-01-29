package dev.arch3rtemp.feature.tvshow.ui.util

import androidx.recyclerview.widget.DiffUtil
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class TvShowComparator : DiffUtil.ItemCallback<TvShowUi>() {
    override fun areItemsTheSame(oldItem: TvShowUi, newItem: TvShowUi): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TvShowUi, newItem: TvShowUi): Boolean =
        oldItem == newItem
}