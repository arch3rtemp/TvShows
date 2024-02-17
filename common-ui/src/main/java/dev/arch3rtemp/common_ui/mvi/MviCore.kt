package dev.arch3rtemp.common_ui.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MviCore<State : UiState, Action : UiAction, Effect : UiEffect> {

    val state: StateFlow<State>
    val effect: Flow<Effect>

    fun onAction(action: Action)
    fun updateState(reduce: State.() -> State)
    fun ViewModel.emitSideEffect(effect: Effect)
}