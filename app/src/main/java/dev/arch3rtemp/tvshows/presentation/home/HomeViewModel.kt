package dev.arch3rtemp.tvshows.presentation.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.common.util.formatErrorMessage
import dev.arch3rtemp.tvshows.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.tvshows.domain.interactor.SearchTvShowsInteractor
import dev.arch3rtemp.tvshows.domain.model.TvShow
import dev.arch3rtemp.tvshows.presentation.commonui.base.BaseViewModel
import dev.arch3rtemp.tvshows.presentation.util.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularTvShowsInteractor: GetPopularTvShowsInteractor,
    private val searchTvShowsInteractor: SearchTvShowsInteractor
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var totalTvShows: List<TvShow> = emptyList()
    private var totalSearchedTvShows: List<TvShow> = emptyList()

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Error(exception.localizedMessage ?: ""))
            }
        }

        loadTvShows(Constants.FIRST_PAGE, false)
    }

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(homeViewState = HomeContract.HomeViewState.Idle)
    }

    override fun handleEvent(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.OnLoadTvShows -> loadTvShows(event.page, event.isRefreshing)
            is HomeContract.Event.OnSearchQuerySubmitted -> searchTvShows(event.query, event.page)
        }
    }

    private fun loadTvShows(page: Int, isRefreshing: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }

            when(val result = getPopularTvShowsInteractor(page)) {
                is dev.arch3rtemp.common.util.Resource.Error -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    formatErrorMessage(result.code, result.message)
                )) }
                is dev.arch3rtemp.common.util.Resource.Exception -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(result.e.localizedMessage)) }
                is dev.arch3rtemp.common.util.Resource.Success -> {

                    if (totalSearchedTvShows.isNotEmpty()) {
                        setState { copy(homeViewState = HomeContract.HomeViewState.Success(totalSearchedTvShows)) }
                        return@launch
                    }

                    totalTvShows = if (isRefreshing) {
                        result.data
                    } else {
                        totalTvShows + result.data
                    }
                    setState { copy(homeViewState = HomeContract.HomeViewState.Success(totalTvShows)) }
                }
            }
        }
    }

    private fun searchTvShows(query: String, page: Int) {
        if (query.isNullOrBlank()) {
            totalSearchedTvShows = emptyList()
            setState { copy(homeViewState = HomeContract.HomeViewState.Success(totalTvShows)) }
            return
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }

            when(val result = searchTvShowsInteractor(query, page)) {
                is dev.arch3rtemp.common.util.Resource.Error -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    formatErrorMessage(result.code, result.message)
                )) }
                is dev.arch3rtemp.common.util.Resource.Exception -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(result.e.localizedMessage)) }
                is dev.arch3rtemp.common.util.Resource.Success -> {
                    totalSearchedTvShows = totalSearchedTvShows + result.data
                    setState { copy(homeViewState = HomeContract.HomeViewState.Success(totalSearchedTvShows)) }
                }
            }
        }
    }
}