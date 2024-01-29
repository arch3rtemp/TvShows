package dev.arch3rtemp.feature.tvshow.ui.detail

import coil.load
import dev.arch3rtemp.common.util.formatImageUrl
import dev.arch3rtemp.common_ui.base.BaseViewHolder
import dev.arch3rtemp.feature.tvshow.databinding.SimilarViewLayoutBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class DetailViewHolder(
    private val binding: SimilarViewLayoutBinding,
    private val clickListener: (TvShowUi) -> Unit
) : BaseViewHolder<TvShowUi, SimilarViewLayoutBinding>(binding) {

    override fun bind() {
        getRowItem()?.let {  tvShow ->
            binding.apply {
                ivSimilar.load(formatImageUrl(tvShow.posterPath)) {
                    crossfade(true)
                }
                tvSimilarTitle.text = tvShow.name
                cardView.setOnClickListener {
                    clickListener(tvShow)
                }
            }
        }
    }
}