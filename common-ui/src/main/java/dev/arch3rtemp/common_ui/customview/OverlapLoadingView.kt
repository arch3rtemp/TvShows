package dev.arch3rtemp.common_ui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import dev.arch3rtemp.common_ui.databinding.OverlapLoadingViewLayoutBinding

class OverlapLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: OverlapLoadingViewLayoutBinding

    init {
        binding = OverlapLoadingViewLayoutBinding.inflate(LayoutInflater.from(context), this)
    }

    fun loadingStateType(stateType: StateType) {
        when (stateType) {
            StateType.LOADING -> {
                binding.apply {
                    loadingView.visibility = View.VISIBLE
                    progressBar.visibility = View.VISIBLE
                    ivIcon.visibility = View.GONE
                }
            }
            StateType.ERROR -> {
                binding.apply {
                    loadingView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    ivIcon.visibility = View.VISIBLE
                }
            }
            StateType.DONE -> {
                binding.loadingView.visibility = View.GONE
            }
        }
    }

    enum class StateType {
        ERROR, LOADING, DONE
    }
}