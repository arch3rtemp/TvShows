package dev.arch3rtemp.feature.tvshow.ui.detail

import dev.arch3rtemp.common_ui.UiEffect
import dev.arch3rtemp.common_ui.UiEvent
import dev.arch3rtemp.common_ui.UiState
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

    sealed interface Event : UiEvent {
        data class OnDetailLoaded(val tvShow: TvShowUi) : Event
        data class OnDetailError(val message: String?) : Event
        data class OnSimilarsLoaded(val seriesId: String, val page: Int) : Event
        data class OnSimilarClicked(val tvShow: TvShowUi) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect
    }

    data class State(
        val detailViewState: DetailViewState,
        val similarsViewState: SimilarsViewState
    ) : UiState
}