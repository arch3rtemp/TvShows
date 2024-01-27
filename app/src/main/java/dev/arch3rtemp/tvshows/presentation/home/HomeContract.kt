package dev.arch3rtemp.tvshows.presentation.home

import dev.arch3rtemp.tvshows.presentation.commonui.UiEffect
import dev.arch3rtemp.tvshows.presentation.commonui.UiEvent
import dev.arch3rtemp.tvshows.domain.model.TvShow
import dev.arch3rtemp.tvshows.presentation.commonui.UiState

class HomeContract {
    sealed interface HomeViewState {
        data object Idle : HomeViewState
        data object Loading : HomeViewState
        data object Empty : HomeViewState
        data class Error(val message :String?) : HomeViewState
        data class Success(val tvShows: List<TvShow> = emptyList()) : HomeViewState
    }

    sealed interface Event : UiEvent {
        data class OnLoadTvShows(val page: Int, val isRefreshing: Boolean) : Event
        data class OnSearchQuerySubmitted(val query: String, val page: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect
    }

    data class State(val homeViewState: HomeViewState) : UiState
}