package dev.arch3rtemp.tvshows.presentation.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.tvshows.common.Resource
import dev.arch3rtemp.tvshows.domain.interactor.GetPopularTvShowsInteractor
import dev.arch3rtemp.tvshows.presentation.commonui.base.BaseViewModel
import dev.arch3rtemp.tvshows.presentation.util.Constants
import dev.arch3rtemp.tvshows.util.formatErrorMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularTvShowsInteractor: GetPopularTvShowsInteractor
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Error(exception.localizedMessage ?: ""))
            }
        }

        loadTvShows(Constants.FIRST_PAGE)
    }

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(homeViewState = HomeContract.HomeViewState.Idle)
    }

    override fun handleEvent(event: HomeContract.Event) {
        when(event) {
            is HomeContract.Event.OnLoadTvShows -> loadTvShows(event.page)
        }
    }

    private fun loadTvShows(page: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            setState {
                copy(homeViewState = HomeContract.HomeViewState.Loading)
            }
            val result = getPopularTvShowsInteractor(page)

            when(result) {
                is Resource.Error -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(formatErrorMessage(result.code, result.message))) }
                is Resource.Exception -> setState { copy(homeViewState = HomeContract.HomeViewState.Error(result.e.localizedMessage)) }
                is Resource.Success -> setState { copy(homeViewState = HomeContract.HomeViewState.Success(result.data)) }
            }
        }
    }
}