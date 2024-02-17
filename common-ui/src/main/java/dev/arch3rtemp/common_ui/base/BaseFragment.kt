package dev.arch3rtemp.common_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import dev.arch3rtemp.common_ui.UiAction
import dev.arch3rtemp.common_ui.UiEffect
import dev.arch3rtemp.common_ui.UiState
import kotlinx.coroutines.launch

abstract class BaseFragment<Action : UiAction, State : UiState, Effect : UiEffect, VB : ViewBinding, VM : BaseViewModel<Action, State, Effect>> : Fragment() {

    private var _binding: VB? = null
    abstract val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected abstract val viewModel: VM

    protected val binding: VB
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindLayout.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    renderState(it)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    renderEffect(it)
                }
            }
        }

        prepareView(savedInstanceState)
    }

    abstract fun prepareView(savedInstanceState: Bundle?)

    abstract fun renderState(state: State)

    abstract fun renderEffect(effect: Effect)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}