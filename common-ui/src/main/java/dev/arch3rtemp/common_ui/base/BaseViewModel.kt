package dev.arch3rtemp.common_ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.arch3rtemp.common_ui.mvi.UiAction
import dev.arch3rtemp.common_ui.mvi.UiEffect
import dev.arch3rtemp.common_ui.mvi.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<Action : UiAction, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _action: MutableSharedFlow<Action> = MutableSharedFlow()
    val action = _action.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            action.collect {
                handleAction(it)
            }
        }
    }

    abstract fun handleAction(action: Action)

    fun setAction(action: Action) {
        val newAction = action
        viewModelScope.launch { _action.emit(newAction) }
    }

    protected fun setState(reduce: State.() -> State) {
        _uiState.update(reduce)
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}