package dev.arch3rtemp.tvshows.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import dev.arch3rtemp.common_ui.base.BaseRVAdapter
import dev.arch3rtemp.tvshows.databinding.TvshowViewLayoutBinding
import dev.arch3rtemp.tvshows.domain.model.TvShow

class HomeAdapter(private val clickListener: (Int) -> Unit)
    : BaseRVAdapter<TvShow, TvshowViewLayoutBinding, HomeViewHolder>(TV_SHOW_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemBinding = TvshowViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(itemBinding, clickListener)
    }

    companion object {
        private val TV_SHOW_COMPARATOR = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean =
                oldItem == newItem
        }
    }
}