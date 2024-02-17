package dev.arch3rtemp.common_ui.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MviCoreImpl<State : UiState, Action : UiAction, Effect : UiEffect> internal constructor(
    initialState: State
) : MviCore<State, Action, Effect> {

    private val _state = MutableStateFlow(initialState)
    override val state: StateFlow<State> = _state.asStateFlow()

    private val _effect by lazy { Channel<Effect>() }
    override val effect: Flow<Effect> by lazy { _effect.receiveAsFlow() }

    override fun onAction(action: Action) {}

    override fun updateState(reduce: State.() -> State) {
        _state.update(reduce)
    }

    override fun ViewModel.emitSideEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}