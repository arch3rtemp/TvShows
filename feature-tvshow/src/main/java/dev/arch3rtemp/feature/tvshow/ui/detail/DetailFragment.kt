package dev.arch3rtemp.feature.tvshow.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.common_ui.base.BaseFragment
import dev.arch3rtemp.feature.tvshow.databinding.FragmentDetailBinding

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailContract.Event, DetailContract.State, DetailContract.Effect, FragmentDetailBinding, DetailViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = TODO("Not yet implemented")
    override val viewModel: DetailViewModel
        get() = TODO("Not yet implemented")

    override fun prepareView(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun renderEffect(effect: DetailContract.Effect) {
        TODO("Not yet implemented")
    }

    override fun renderState(state: DetailContract.State) {
        TODO("Not yet implemented")
    }

}