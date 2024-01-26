package dev.arch3rtemp.tvshows.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.tvshows.R
import dev.arch3rtemp.tvshows.databinding.FragmentHomeBinding
import dev.arch3rtemp.tvshows.presentation.commonui.DebounceQueryTextListener
import dev.arch3rtemp.tvshows.presentation.commonui.base.BaseFragment
import dev.arch3rtemp.tvshows.presentation.commonui.customview.PaginationRecyclerView
import dev.arch3rtemp.tvshows.presentation.util.Constants
import dev.arch3rtemp.tvshows.util.SnackbarStatusCodes
import dev.arch3rtemp.tvshows.util.showSnackbar


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeContract.Event, HomeContract.State, HomeContract.Effect, FragmentHomeBinding, HomeViewModel>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override val viewModel by viewModels<HomeViewModel>()

    private var tvShowAdapter: HomeAdapter? = null

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
            is HomeContract.Effect.ShowSnackBar -> showSnackbar(effect.message.asString(requireContext()), SnackbarStatusCodes.ERROR)
        }
    }

    private fun setupToolbar() = with(binding) {

        // Find the search item and set up the SearchView
        val searchItem = toolbar.menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(
            DebounceQueryTextListener(
                lifecycle = viewLifecycleOwner.lifecycle,
                onDebounceQueryTextChange = { query ->
                    searchQuery(query)
                    searchView.clearFocus()
                }))
    }

    private fun searchQuery(query: String) {
        viewModel.setEvent(HomeContract.Event.OnSearchQuerySubmitted(query, Constants.FIRST_PAGE))
    }

    private fun setupRecyclerView() = with(binding) {
        tvShowAdapter = HomeAdapter {  }
        rvTrendingList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = tvShowAdapter
            setOnPageChangeListener(object : PaginationRecyclerView.OnPageChangeListener {
                override fun onPageChange(page: Int) {
                    viewModel.setEvent(HomeContract.Event.OnLoadTvShows(page, false))
                }
            })
        }
    }

    private fun setListeners() {
        binding.swipe.setOnRefreshListener {
            viewModel.setEvent(HomeContract.Event.OnLoadTvShows(Constants.FIRST_PAGE, true))
            binding.swipe.isRefreshing = false
            val searchItem = binding.toolbar.menu.findItem(R.id.action_search)
            val searchView = searchItem.actionView as SearchView
            if (!searchView.isIconified) {
                searchItem.collapseActionView()
            }
        }
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
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}