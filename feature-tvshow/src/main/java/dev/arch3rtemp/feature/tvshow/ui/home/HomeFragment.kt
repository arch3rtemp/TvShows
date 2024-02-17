package dev.arch3rtemp.feature.tvshow.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.common_ui.DebounceQueryTextListener
import dev.arch3rtemp.common_ui.base.BaseFragment
import dev.arch3rtemp.common_ui.customview.EmptyView
import dev.arch3rtemp.common_ui.customview.PaginationRecyclerView
import dev.arch3rtemp.common_ui.util.Constants
import dev.arch3rtemp.common_ui.util.SnackbarStatusCodes
import dev.arch3rtemp.common_ui.util.checkError
import dev.arch3rtemp.common_ui.util.showSnackbar
import dev.arch3rtemp.feature.tvshow.R
import dev.arch3rtemp.feature.tvshow.databinding.FragmentHomeBinding
import dev.arch3rtemp.feature.tvshow.ui.detail.DetailFragment
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import dev.arch3rtemp.feature.tvshow.ui.navigation.DetailsScreenimpl
import dev.arch3rtemp.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeContract.Action, HomeContract.State, HomeContract.Effect, FragmentHomeBinding, HomeViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var navigator: Navigator

    private var homeAdapter: HomeAdapter? = null

    private var searchView: SearchView? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        setupToolbar()
        setupRecyclerView()
        setListeners()
        setInitState()
    }

    override fun renderState(state: HomeContract.State) {
        when(state.homeViewState) {
            HomeContract.HomeViewState.Idle -> {
                emptyViewStatus(EmptyView.StateType.EMPTY, null)
                swipeLoaderStatus(false)
            }
            HomeContract.HomeViewState.Loading -> {
                emptyViewStatus(EmptyView.StateType.NO_ERROR, null)
                swipeLoaderStatus(true)
            }
            HomeContract.HomeViewState.Empty -> {
                emptyViewStatus(EmptyView.StateType.EMPTY, null)
                swipeLoaderStatus(false)
            }
            is HomeContract.HomeViewState.Error -> {
                val type = checkError(state.homeViewState.throwable)
                val listener = getListener(type)
                emptyViewStatus(type, listener)
                swipeLoaderStatus(false)
            }
            is HomeContract.HomeViewState.Success -> {
                emptyViewStatus(EmptyView.StateType.NO_ERROR, null)
                swipeLoaderStatus(true)
                homeAdapter?.submitList(state.homeViewState.tvShows)
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
        viewModel.onAction(HomeContract.Action.OnLoadTvShows(page, isRefreshing))
    }

    private fun searchQuery(query: String, page: Int = Constants.FIRST_PAGE) {
        viewModel.onAction(HomeContract.Action.OnSearchQuerySubmitted(query, page))
    }

    private fun setupRecyclerView() = with(binding) {
        homeAdapter = HomeAdapter { tvShow ->
            navigateToDetails(tvShow)
        }
        rvTrendingList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = homeAdapter
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

    private fun setInitState() {
        viewModel.onAction(HomeContract.Action.OnLoadTvShows(Constants.FIRST_PAGE, false))
    }

    private fun navigateToDetails(tvShow: TvShowUi) {
        val bundle = Bundle().apply {
            putParcelable(DetailFragment.ARG_TV_SHOW, tvShow)
        }
        val detailScreen = DetailsScreenimpl().apply {
            destination(bundle)
        }
        navigator.navigateWithReplaceTo(
            detailScreen,
            allowingStateLoss = false,
            addToBackStack = true,
            allowReordering = true
        )
    }

    private fun swipeLoaderStatus(visibility: Boolean) = with(binding) {
        if (visibility) {
            swipe.visibility = View.VISIBLE
        } else {
            swipe.visibility = View.GONE
        }
    }

    private fun emptyViewStatus(status: EmptyView.StateType, listener: OnClickListener?) = with(binding) {
        emptyView.emptyStateType(status, listener)
    }

    private fun getListener(type: EmptyView.StateType): OnClickListener? {
        return if (type == EmptyView.StateType.CONNECTION) {
            OnClickListener {
                loadTvShows(false)
            }
        } else {
            null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        homeAdapter = null
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