package dev.arch3rtemp.feature.tvshow.ui.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.common.util.Resource
import dev.arch3rtemp.common.util.formatErrorMessage
import dev.arch3rtemp.common_ui.base.BaseViewModel
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
) : BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    
    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.tag(TAG).d(throwable)
            setEffect { DetailContract.Effect.ShowSnackBar(throwable.localizedMessage ?: "") }
        }
    }
    override fun createInitialState(): DetailContract.State {
        return DetailContract.State(
            DetailContract.DetailViewState.Idle,
            DetailContract.SimilarsViewState.Idle
        )
    }

    override fun handleEvent(event: DetailContract.Event) {
        when(event) {
            is DetailContract.Event.OnDetailLoaded -> {
                setState { 
                    copy(detailViewState = DetailContract.DetailViewState.Success(event.tvShow))
                }
            }
            is DetailContract.Event.OnDetailError -> {
                setState { 
                    copy(detailViewState = DetailContract.DetailViewState.Error(event.message))
                }
                if (event.message != null) setEffect { DetailContract.Effect.ShowSnackBar(event.message) }
            }
            is DetailContract.Event.OnLoadSimilars -> {
                Timber.tag(TAG).d("OnLoadSimilars")
                loadSimilarTvShows(event.seriesId, event.page) }
        }
    }
    
    private fun loadSimilarTvShows(seriesId: String, page: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            setState {
                copy(similarsViewState = DetailContract.SimilarsViewState.Loading)
            }

            when(val result = getSimilarTvShowsInteractor(seriesId, page)) {
                is Resource.Error -> {
                    setState {
                        copy(similarsViewState = DetailContract.SimilarsViewState.Error(
                            formatErrorMessage(result.code, result.message)
                        ))
                    }
                }
                is Resource.Exception -> {
                    setState {
                        copy(similarsViewState = DetailContract.SimilarsViewState.Error(result.e.localizedMessage))
                    }
                }
                is Resource.Success -> setState {
                    val tvShowList = tvShowUiDomainMapper.toList(result.data)
                    Timber.tag(TAG).d(tvShowList.isEmpty().toString())
                    if (tvShowList.isEmpty()) {
                        copy(similarsViewState = DetailContract.SimilarsViewState.Empty)
                    } else {
                        copy(similarsViewState = DetailContract.SimilarsViewState.Success(tvShowList))
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "detail_view_model"
    }
}