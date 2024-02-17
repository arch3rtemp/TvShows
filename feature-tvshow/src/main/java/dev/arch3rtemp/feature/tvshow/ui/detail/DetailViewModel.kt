package dev.arch3rtemp.feature.tvshow.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.common_ui.mvi.MviCore
import dev.arch3rtemp.common_ui.mvi.mviDelegate
import dev.arch3rtemp.feature.tvshow.domain.interactor.GetSimilarTvShowsInteractor
import dev.arch3rtemp.feature.tvshow.ui.mapper.TvShowUiDomainMapper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getSimilarTvShowsInteractor: GetSimilarTvShowsInteractor,
    private val tvShowUiDomainMapper: TvShowUiDomainMapper
) : ViewModel(),
    MviCore<DetailContract.State, DetailContract.Action, DetailContract.Effect> by mviDelegate(
        initialState = createInitialState()
    ) {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    
    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.tag(TAG).d(throwable)
            emitSideEffect(DetailContract.Effect.ShowSnackBar(throwable.localizedMessage ?: ""))
        }
    }

    override fun onAction(action: DetailContract.Action) {
        when(action) {
            is DetailContract.Action.OnDetailLoaded -> {
                updateState {
                    copy(detailViewState = DetailContract.DetailViewState.Success(action.tvShow))
                }
            }
            is DetailContract.Action.OnDetailError -> {
                updateState {
                    copy(detailViewState = DetailContract.DetailViewState.Error(action.message))
                }
                if (action.message != null) emitSideEffect(DetailContract.Effect.ShowSnackBar(action.message))
            }
            is DetailContract.Action.OnLoadSimilars -> {
                Timber.tag(TAG).d("OnLoadSimilars")
                loadSimilarTvShows(action.seriesId, action.page) }
        }
    }
    
    private fun loadSimilarTvShows(seriesId: String, page: Int) {

        viewModelScope.launch(coroutineExceptionHandler) {
            updateState {
                copy(similarsViewState = DetailContract.SimilarsViewState.Loading)
            }

            val result = getSimilarTvShowsInteractor(seriesId, page)
            result
                .onSuccess { data ->
                    val tvShowList = tvShowUiDomainMapper.toList(data)
                    Timber.tag(TAG).d(tvShowList.isEmpty().toString())
                    if (tvShowList.isEmpty()) {
                        updateState {
                            copy(similarsViewState = DetailContract.SimilarsViewState.Empty)
                        }
                    } else {
                        updateState {
                            copy(
                                similarsViewState = DetailContract.SimilarsViewState.Success(
                                    tvShowList
                                )
                            )
                        }
                    }
                }
                .onFailure { throwable ->
                    updateState {
                        copy(
                            similarsViewState = DetailContract.SimilarsViewState.Error(
                                throwable.localizedMessage
                            )
                        )
                    }
                }
        }
    }

    companion object {
        private const val TAG = "detail_view_model"

        fun createInitialState() = DetailContract.State(
            DetailContract.DetailViewState.Idle,
            DetailContract.SimilarsViewState.Idle
        )
    }
}