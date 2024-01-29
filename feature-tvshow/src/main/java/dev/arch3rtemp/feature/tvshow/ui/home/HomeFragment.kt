package dev.arch3rtemp.feature.tvshow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.common_ui.DebounceQueryTextListener
import dev.arch3rtemp.common_ui.base.BaseFragment
import dev.arch3rtemp.common_ui.customview.PaginationRecyclerView
import dev.arch3rtemp.common_ui.util.Constants
import dev.arch3rtemp.common_ui.util.SnackbarStatusCodes
import dev.arch3rtemp.common_ui.util.showSnackbar
import dev.arch3rtemp.feature.tvshow.R
import dev.arch3rtemp.feature.tvshow.databinding.FragmentHomeBinding
import dev.arch3rtemp.feature.tvshow.ui.adapter.TvShowAdapter
import dev.arch3rtemp.feature.tvshow.ui.detail.DetailFragment
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.feature.tvshow.ui.navigation.DetailsScreenimpl

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeContract.Event, HomeContract.State, HomeContract.Effect, FragmentHomeBinding, HomeViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel by viewModels<HomeViewModel>()

//    @Inject
//    lateinit var navigator: Navigator

    private var tvShowAdapter: TvShowAdapter? = null

    private var searchView: SearchView? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerView()
        setListeners()
    }

    override fun renderState(state: HomeContract.State) {
        when(state.homeViewState) {
            HomeContract.HomeViewState.Idle -> Unit
            HomeContract.HomeViewState.Loading -> {
//                showLoading()
            }
            HomeContract.HomeViewState.Empty -> {
                showEmptyView()
            }
            is HomeContract.HomeViewState.Error -> {
                hideEmptyView()
            }
            is HomeContract.HomeViewState.Success -> {
                hideEmptyView()
                tvShowAdapter?.submitList(state.homeViewState.tvShows)
            }
        }
    }

    override fun renderEffect(effect: HomeContract.Effect) {
        when(effect) {
            is HomeContract.Effect.ShowSnackBar -> showSnackbar(effect.message, SnackbarStatusCodes.ERROR)
        }
    }

    private fun setupToolbar() = with(binding) {

        searchView = toolbar.menu.findItem(R.id.action_search).actionView as SearchView
        searchView?.setOnQueryTextListener(
            DebounceQueryTextListener(
                lifecycle = viewLifecycleOwner.lifecycle,
                onDebounceQueryTextChange = { query ->
                    searchQuery(query)
                })
        )
    }

    private fun loadTvShows(isRefreshing: Boolean, page: Int = Constants.FIRST_PAGE) {
        viewModel.setEvent(HomeContract.Event.OnLoadTvShows(page, isRefreshing))
    }

    private fun searchQuery(query: String, page: Int = Constants.FIRST_PAGE) {
        viewModel.setEvent(HomeContract.Event.OnSearchQuerySubmitted(query, page))
    }

    private fun setupRecyclerView() = with(binding) {
        tvShowAdapter = TvShowAdapter { tvShow ->
            navigateToDetails(tvShow)
        }
        rvTrendingList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tvShowAdapter
            setOnPageChangeListener(object : PaginationRecyclerView.OnPageChangeListener {
                override fun onPageChange(page: Int) {
                    searchView?.let {
                        val query = it.query
                        if (query.isNotBlank()) {
                            searchQuery(query.toString(), page)
                        } else {
                            loadTvShows(false, page)
                        }
                    }
                }
            })
        }
    }

    private fun setListeners() {
        binding.swipe.setOnRefreshListener {
            loadTvShows(true)
            binding.swipe.isRefreshing = false
            val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            if (!searchView.isIconified) {
                searchItem.collapseActionView()
            }
        }
    }

    private fun navigateToDetails(tvShow: TvShowUi) {
        val detailScreen = DetailsScreenimpl()
        val bundle = Bundle()
        bundle.putParcelable(DetailFragment.ARG_TV_SHOW, tvShow)
        detailScreen.destination(bundle)
//        navigator.navigateWithReplaceTo(
//            detailScreen,
//            allowingStateLoss = false,
//            addToBackStack = true,
//            allowReordering = true
//        )
    }

    private fun showEmptyView() = with(binding) {
        emptyView.visibility = View.VISIBLE
        swipe.visibility = View.GONE
    }

    private fun hideEmptyView() = with(binding) {
        emptyView.visibility = View.GONE
        swipe.visibility = View.VISIBLE
    }

    companion object {

        const val TAG = "home_fragment"

        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return - A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}