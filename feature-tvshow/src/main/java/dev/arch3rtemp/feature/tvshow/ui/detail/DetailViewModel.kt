package dev.arch3rtemp.feature.tvshow.ui.detail

import dev.arch3rtemp.common_ui.base.BaseViewModel
import dev.arch3rtemp.common_ui.util.Constants
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

class DetailViewModel @Inject constructor(

) : BaseViewModel<DetailContract.Event, DetailContract.State, DetailContract.Effect>() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    
    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            setEffect { DetailContract.Effect.ShowSnackBar(throwable.localizedMessage ?: "") }
        }
        
        loadSimilarTvShows(Constants.FIRST_PAGE)
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
            is DetailContract.Event.OnSimilarsLoaded -> { loadSimilarTvShows(event.page) }
            is DetailContract.Event.OnSimilarClicked -> TODO()
        }
    }
    
    private fun loadSimilarTvShows(page: Int) {
        
    }
}