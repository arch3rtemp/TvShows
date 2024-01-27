package dev.arch3rtemp.tvshows.presentation.home

import coil.load
import dev.arch3rtemp.common.util.formatImageUrl
import dev.arch3rtemp.common.util.roundToTenth
import dev.arch3rtemp.tvshows.databinding.TvshowViewLayoutBinding
import dev.arch3rtemp.tvshows.domain.model.TvShow
import dev.arch3rtemp.tvshows.presentation.commonui.base.BaseViewHolder

class HomeViewHolder(
    private val binding: TvshowViewLayoutBinding,
    private val clickListener: (Int) -> Unit
    ) :
    BaseViewHolder<TvShow, TvshowViewLayoutBinding>(binding) {
    override fun bind() {
        getRowItem()?.let { tvShow ->
            binding.apply {
                tvMovieTitle.text = tvShow.name
                val rating = tvShow.voteAverage.roundToTenth()
                tvVoteAverage.text = rating.toString()
                ivMovie.load(formatImageUrl(tvShow.posterPath)) {
                    crossfade(true)
                }
                cardView.setOnClickListener { clickListener(tvShow.id) }
            }
        }
    }
}