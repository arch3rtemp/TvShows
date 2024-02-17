package dev.arch3rtemp.feature.tvshow.ui.detail

import dev.arch3rtemp.common_ui.mvi.UiEffect
import dev.arch3rtemp.common_ui.mvi.UiAction
import dev.arch3rtemp.common_ui.mvi.UiState
import dev.arch3rtemp.feature.tvshow.ui.model.TvShowUi

class DetailContract {
    sealed interface DetailViewState {
        data object Idle : DetailViewState
        data object Loading : DetailViewState
        data class Error(val message: String?) : DetailViewState
        data class Success(val tvShow: TvShowUi) : DetailViewState
    }

    sealed interface SimilarsViewState {
        data object Idle : SimilarsViewState
        data object Loading : SimilarsViewState
        data object Empty : SimilarsViewState
        data class Error(val message: String?) : SimilarsViewState
        data class Success(val similarShows: List<TvShowUi> = emptyList()) : SimilarsViewState
    }

    sealed interface Action : UiAction {
        data class OnDetailLoaded(val tvShow: TvShowUi) : Action
        data class OnDetailError(val message: String?) : Action
        data class OnLoadSimilars(val seriesId: String, val page: Int) : Action
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect
    }

    data class State(
        val detailViewState: DetailViewState,
        val similarsViewState: SimilarsViewState
    ) : UiState
}