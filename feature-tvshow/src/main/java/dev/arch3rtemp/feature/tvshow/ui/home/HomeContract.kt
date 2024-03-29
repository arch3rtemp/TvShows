package dev.arch3rtemp.feature.tvshow.ui.home

import dev.arch3rtemp.common_ui.mvi.UiAction
import dev.arch3rtemp.common_ui.mvi.UiEffect
import dev.arch3rtemp.common_ui.mvi.UiState
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class HomeContract {
    sealed interface HomeViewState {
        data object Idle : HomeViewState
        data object Loading : HomeViewState
        data object Empty : HomeViewState
        data class Error(val throwable: Throwable) : HomeViewState
        data class Success(val tvShows: List<TvShowUi> = emptyList()) : HomeViewState
    }

    sealed interface Action : UiAction {
        data class OnLoadTvShows(val page: Int, val isRefreshing: Boolean) : Action
        data class OnSearchQuerySubmitted(val query: String, val page: Int) : Action
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect
    }

    data class State(val homeViewState: HomeViewState) : UiState
}