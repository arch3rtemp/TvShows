package dev.arch3rtemp.feature.tvshow.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.common.util.formatImageUrl
import dev.arch3rtemp.common.util.roundToTenth
import dev.arch3rtemp.common_ui.R
import dev.arch3rtemp.common_ui.base.BaseFragment
import dev.arch3rtemp.common_ui.util.SnackbarStatusCodes
import dev.arch3rtemp.common_ui.util.showSnackbar
import dev.arch3rtemp.feature.tvshow.databinding.FragmentDetailBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailContract.Event, DetailContract.State, DetailContract.Effect, FragmentDetailBinding, DetailViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override val viewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val tvShow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_TV_SHOW, TvShowUi::class.java)
            } else {
                it.getParcelable(ARG_TV_SHOW)
            }
            if (tvShow != null) {
                viewModel.setEvent(DetailContract.Event.OnDetailLoaded(tvShow))
            } else {
                viewModel.setEvent(DetailContract.Event.OnDetailError(getString(R.string.operational_error_message)))
            }
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
//        setupRecyclerView()
//        setListeners()
    }

    override fun renderEffect(effect: DetailContract.Effect) {
        when(effect) {
            is DetailContract.Effect.ShowSnackBar -> showSnackbar(effect.message, SnackbarStatusCodes.ERROR)
        }
    }

    override fun renderState(state: DetailContract.State) {
        renderDetails(state.detailViewState)
        renderSimilarsState(state.similarsViewState)
    }

    private fun renderDetails(state: DetailContract.DetailViewState) {
        when(state) {
            DetailContract.DetailViewState.Idle -> Unit
            DetailContract.DetailViewState.Loading -> Unit
            is DetailContract.DetailViewState.Error -> Unit
            is DetailContract.DetailViewState.Success -> fillInDetails(state.tvShow)
        }
    }

    private fun renderSimilarsState(state: DetailContract.SimilarsViewState) {
        when(state) {
            DetailContract.SimilarsViewState.Idle -> Unit
            DetailContract.SimilarsViewState.Loading -> Unit
            DetailContract.SimilarsViewState.Empty -> Unit
            is DetailContract.SimilarsViewState.Error -> Unit
            is DetailContract.SimilarsViewState.Success -> Unit
        }
    }

    private fun fillInDetails(tvShow: TvShowUi) = with(binding) {
        ivBackdrop.load(formatImageUrl(tvShow.backdropPath)) {
            crossfade(true)
        }
        ivPoster.load(formatImageUrl(tvShow.posterPath)) {
            crossfade(true)
        }
        tvShowTitleValue.text = tvShow.name
        tvVoteAverage.text = tvShow.voteAverage.roundToTenth().toString()
        tvDescriptionValue.text = tvShow.overview


    }

    companion object {

        const val TAG = "detail_fragment"
        const val ARG_TV_SHOW = "arg_tv_show"

        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @param tvShow - A data object containing chosen Tv Show details.
         * @return - A new instance of fragment DetailFragment.
         */
        @JvmStatic
        fun newInstance(args: Bundle): DetailFragment {

            return DetailFragment().apply {
                arguments = args
            }
        }
    }
}