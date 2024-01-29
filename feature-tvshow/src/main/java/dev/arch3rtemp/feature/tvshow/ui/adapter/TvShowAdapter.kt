package dev.arch3rtemp.feature.tvshow.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import dev.arch3rtemp.common_ui.base.BaseRVAdapter
import dev.arch3rtemp.feature.tvshow.databinding.TvshowViewLayoutBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class TvShowAdapter(private val clickListener: (TvShowUi) -> Unit)
    : BaseRVAdapter<TvShowUi, TvshowViewLayoutBinding, TvShowViewHolder>(TV_SHOW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val itemBinding = TvshowViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemBinding, clickListener)
    }

    companion object {
        private val TV_SHOW_COMPARATOR = object : DiffUtil.ItemCallback<TvShowUi>() {
            override fun areItemsTheSame(oldItem: TvShowUi, newItem: TvShowUi): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShowUi, newItem: TvShowUi): Boolean =
                oldItem == newItem
        }
    }
}