package dev.arch3rtemp.tvshows.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.arch3rtemp.tvshows.databinding.FragmentHomeBinding
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
        setupRecyclerView()
        setListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
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

    }

    private fun setupRecyclerView() = with(binding) {
        tvShowAdapter = HomeAdapter {  }
        rvTrendingList.adapter = tvShowAdapter
        rvTrendingList.setOnPageChangeListener(object : PaginationRecyclerView.OnPageChangeListener {
            override fun onPageChange(page: Int) {
                viewModel.setEvent(HomeContract.Event.OnLoadTvShows(page))
            }
        })
    }

    private fun setListeners() {
        binding.swipe.setOnRefreshListener {
            viewModel.setEvent(HomeContract.Event.OnLoadTvShows(Constants.FIRST_PAGE))
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