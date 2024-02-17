package dev.arch3rtemp.feature.tvshow.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.common.util.formatImageUrl
import dev.arch3rtemp.common.util.roundToTenth
import dev.arch3rtemp.common_ui.R
import dev.arch3rtemp.common_ui.base.BaseFragment
import dev.arch3rtemp.common_ui.customview.OverlapLoadingView
import dev.arch3rtemp.common_ui.customview.PaginationRecyclerView
import dev.arch3rtemp.common_ui.util.Constants
import dev.arch3rtemp.common_ui.util.SnackbarStatusCodes
import dev.arch3rtemp.common_ui.util.showSnackbar
import dev.arch3rtemp.feature.tvshow.databinding.FragmentDetailBinding
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailContract.Action, DetailContract.State, DetailContract.Effect, FragmentDetailBinding, DetailViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override val viewModel by viewModels<DetailViewModel>()

    @Inject
    lateinit var navigator: Navigator

    private var detailAdapter: DetailAdapter? = null

    private var seriesId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val tvShow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_TV_SHOW, TvShowUi::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable(ARG_TV_SHOW) as? TvShowUi
            }
            seriesId = tvShow?.id.toString()
            loadSimilars()
            if (tvShow != null) {
                viewModel.onAction(DetailContract.Action.OnDetailLoaded(tvShow))
            } else {
                viewModel.onAction(DetailContract.Action.OnDetailError(getString(R.string.operational_error_message)))
            }
        }
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        setupRecyclerView()
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
            is DetailContract.DetailViewState.Success -> {
                fillInDetails(state.tvShow)
            }
        }
    }

    private fun renderSimilarsState(state: DetailContract.SimilarsViewState) {
        when(state) {
            DetailContract.SimilarsViewState.Idle -> Unit
            DetailContract.SimilarsViewState.Loading -> binding.loadingView.loadingStateType(OverlapLoadingView.StateType.LOADING)
            DetailContract.SimilarsViewState.Empty -> {
                binding.apply {
                    loadingView.loadingStateType(OverlapLoadingView.StateType.DONE)
                    similarContent.visibility = View.INVISIBLE
                }
            }
            is DetailContract.SimilarsViewState.Error -> binding.loadingView.loadingStateType(OverlapLoadingView.StateType.ERROR)
            is DetailContract.SimilarsViewState.Success -> {
                detailAdapter?.submitList(state.similarShows)
                binding.loadingView.loadingStateType(OverlapLoadingView.StateType.DONE)
            }
        }
    }

    private fun setupRecyclerView() = with(binding) {
        detailAdapter = DetailAdapter { tvShow ->
            updateDetails(tvShow)
        }
        rvSimilarShows.apply {
            adapter = detailAdapter
            setOnPageChangeListener(object : PaginationRecyclerView.OnPageChangeListener {
                override fun onPageChange(page: Int) {
                    loadSimilars(page)
                }
            })
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

    private fun loadSimilars(page: Int = Constants.FIRST_PAGE) {
        seriesId?.let {  id ->
            viewModel.onAction(DetailContract.Action.OnLoadSimilars(id, page))
        }
    }

    private fun updateDetails(tvShow: TvShowUi) {
        viewModel.onAction(DetailContract.Action.OnDetailLoaded(tvShow))
        seriesId = tvShow.id.toString()
        loadSimilars()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        detailAdapter = null
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