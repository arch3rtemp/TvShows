package dev.arch3rtemp.feature.tvshow.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.common_ui.mvi.MviCore
import dev.arch3rtemp.common_ui.mvi.mviDelegate
import dev.arch3rtemp.common_ui.util.checkError
import dev.arch3rtemp.feature.tvshow.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.domain.interactor.SearchTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.ui.mapper.TvShowUiDomainMapper
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularTvShowsInteractor: GetPopularTvShowsInteractor,
    private val searchTvShowsInteractor: SearchTvShowsInteractor,
    private val tvShowUiDomainMapper: TvShowUiDomainMapper
) : ViewModel(),
    MviCore<HomeContract.State, HomeContract.Action, HomeContract.Effect> by mviDelegate(
        initialState = createInitialState()
    ) {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var totalTvShows: List<TvShowUi> = emptyList()
    private var totalSearchedTvShows: List<TvShowUi> = emptyList()

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.tag(TAG).d(throwable.localizedMessage)
            updateState {
                copy(homeViewState = HomeContract.HomeViewState.Error(throwable))
            }
        }
    }

    override fun onAction(action: HomeContract.Action) {
        when(action) {
            is HomeContract.Action.OnLoadTvShows -> loadTvShows(action.page, action.isRefreshing)
            is HomeContract.Action.OnSearchQuerySubmitted -> searchTvShows(action.query, action.page)
        }
    }

    private fun loadTvShows(page: Int, isRefreshing: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {
            updateState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }

            val result = getPopularTvShowsInteractor(page)
            result
                .onSuccess { data ->

                    if (totalSearchedTvShows.isNotEmpty()) {
                        updateState {
                            copy(
                                homeViewState = HomeContract.HomeViewState.Success(
                                    totalSearchedTvShows
                                )
                            )
                        }
                        return@launch
                    }
                    val tvShowUiList = tvShowUiDomainMapper.toList(data)
                    totalTvShows = if (isRefreshing) {
                        tvShowUiList
                    } else {
                        totalTvShows + tvShowUiList
                    }
                    updateState { copy(homeViewState = HomeContract.HomeViewState.Success(totalTvShows)) }
                }
                .onFailure { throwable ->
                    Timber.tag(TAG).d(throwable)
                    updateState {
                        copy(homeViewState = HomeContract.HomeViewState.Error(throwable))
                    }
                }
        }
    }

    private fun searchTvShows(query: String, page: Int) {

        if (query.isNullOrBlank()) {
            totalSearchedTvShows = emptyList()
            updateState { copy(homeViewState = HomeContract.HomeViewState.Success(totalTvShows)) }
            return
        }

        viewModelScope.launch(coroutineExceptionHandler) {
            updateState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }

            val result = searchTvShowsInteractor(query, page)
            result
                .onSuccess { data ->
                    val tvShowUiList = tvShowUiDomainMapper.toList(data)
                    totalSearchedTvShows = totalSearchedTvShows + tvShowUiList
                    if (totalSearchedTvShows.isEmpty()) {
                        updateState {
                            copy(homeViewState = HomeContract.HomeViewState.Empty)
                        }
                    } else {
                        updateState {
                            copy(
                                homeViewState = HomeContract.HomeViewState.Success(totalSearchedTvShows)
                            )
                        }
                    }
                }
                .onFailure { throwable ->
                    updateState {
                        copy(homeViewState = HomeContract.HomeViewState.Error(throwable))
                    }
                }
        }
    }

    companion object {
        private const val TAG = "home_view_model"

        fun createInitialState() = HomeContract.State(homeViewState = HomeContract.HomeViewState.Idle)
    }
}