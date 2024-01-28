package dev.arch3rtemp.feature.tvshow.ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.common.util.formatErrorMessage
import dev.arch3rtemp.common_ui.base.BaseViewModel
import dev.arch3rtemp.common_ui.util.Constants
import dev.arch3rtemp.feature.tvshow.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.domain.interactor.SearchTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.ui.mapper.TvShowUiDomainMapper
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularTvShowsInteractor: GetPopularTvShowsInteractor,
    private val searchTvShowsInteractor: SearchTvShowsInteractor,
    private val tvShowUiDomainMapper: TvShowUiDomainMapper
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var totalTvShows: List<TvShowUi> = emptyList()
    private var totalSearchedTvShows: List<TvShowUi> = emptyList()

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Error(
                    throwable.localizedMessage ?: ""
                )
                )
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
            is HomeContract.Event.OnTvShowClicked -> TODO()
        }
    }

    private fun loadTvShows(page: Int, isRefreshing: Boolean) {
        viewModelScope.launch(coroutineExceptionHandler) {
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }

            when(val result = getPopularTvShowsInteractor(page)) {
                is Resource.Error -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    formatErrorMessage(result.code, result.message)
                )
                ) }
                is Resource.Exception -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    result.e.localizedMessage
                )
                ) }
                is Resource.Success -> {

                    if (totalSearchedTvShows.isNotEmpty()) {
                        setState { copy(homeViewState = HomeContract.HomeViewState.Success(
                            totalSearchedTvShows
                        )
                        ) }
                        return@launch
                    }

                    val tvShowUiList = tvShowUiDomainMapper.toList(result.data)
                    totalTvShows = if (isRefreshing) {
                        tvShowUiList
                    } else {
                        totalTvShows + tvShowUiList
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
                is Resource.Error -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    formatErrorMessage(result.code, result.message)
                )
                ) }
                is Resource.Exception -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(
                    result.e.localizedMessage
                )
                ) }
                is Resource.Success -> {
                    val tvShowUiList = tvShowUiDomainMapper.toList(result.data)
                    totalSearchedTvShows = totalSearchedTvShows + tvShowUiList
                    setState { copy(homeViewState = HomeContract.HomeViewState.Success(
                        totalSearchedTvShows
                    )
                    ) }
                }
            }
        }
    }
}