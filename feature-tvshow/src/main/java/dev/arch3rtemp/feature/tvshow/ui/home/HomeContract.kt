package dev.arch3rtemp.feature.tvshow.ui.home

import dev.arch3rtemp.common_ui.UiEffect
import dev.arch3rtemp.common_ui.UiEvent
import dev.arch3rtemp.common_ui.UiState
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class HomeContract {
    sealed interface HomeViewState {
        data object Idle : HomeViewState
        data object Loading : HomeViewState
        data object Empty : HomeViewState
        data class Error(val message: String?) : HomeViewState
        data class Success(val tvShows: List<TvShowUi> = emptyList()) : HomeViewState
    }

    sealed interface Event : UiEvent {
        data class OnLoadTvShows(val page: Int, val isRefreshing: Boolean) : Event
        data class OnSearchQuerySubmitted(val query: String, val page: Int) : Event
        data class OnTvShowClicked(val tvShow: TvShowUi) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect
    }

    data class State(val homeViewState: HomeViewState) : UiState
}