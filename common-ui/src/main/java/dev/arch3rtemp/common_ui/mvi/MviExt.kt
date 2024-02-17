package dev.arch3rtemp.common_ui.mvi

fun <State : UiState, Action : UiAction, Effect : UiEffect> mviDelegate(
    initialState: State
): MviCore<State, Action, Effect> = MviCoreImpl(initialState)