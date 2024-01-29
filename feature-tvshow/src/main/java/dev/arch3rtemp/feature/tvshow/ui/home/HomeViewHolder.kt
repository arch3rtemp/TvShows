package dev.arch3rtemp.feature.tvshow.ui.home

import coil.load
import dev.arch3rtemp.common.util.formatImageUrl
import dev.arch3rtemp.common.util.roundToTenth
import dev.arch3rtemp.common_ui.base.BaseViewHolder
import dev.arch3rtemp.feature.tvshow.databinding.TvshowViewLayoutBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class HomeViewHolder(
    private val binding: TvshowViewLayoutBinding,
    private val clickListener: (TvShowUi) -> Unit
    ) : BaseViewHolder<TvShowUi, TvshowViewLayoutBinding>(binding) {
    override fun bind() {
        getRowItem()?.let { tvShow ->
            binding.apply {
                tvMovieTitle.text = tvShow.name
                val rating = tvShow.voteAverage.roundToTenth()
                tvVoteAverage.text = rating.toString()
                ivMovie.load(formatImageUrl(tvShow.posterPath)) {
                    crossfade(true)
                }
                cardView.setOnClickListener { clickListener(tvShow) }
            }
        }
    }
}