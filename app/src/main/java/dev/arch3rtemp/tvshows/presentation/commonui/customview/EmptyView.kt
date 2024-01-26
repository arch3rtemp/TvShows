package dev.arch3rtemp.tvshows.presentation.commonui.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import dev.arch3rtemp.tvshows.R
import dev.arch3rtemp.tvshows.databinding.EmptyViewLayoutBinding

class EmptyView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: EmptyViewLayoutBinding

    init {
        binding = EmptyViewLayoutBinding.inflate(LayoutInflater.from(context), this)
    }

    fun emptyStateType(stateType: StateType, listener: OnClickListener?) {
        when (stateType) {
            StateType.NO_ERROR -> {
                binding.emptyStateRoot.visibility = View.GONE
            }
            StateType.CONNECTION -> {
                binding.apply {
                    emptyStateRoot.visibility = View.VISIBLE
                    btnRetry.visibility = View.VISIBLE
                    btnRetry.setOnClickListener(listener)
                    ivState.setImageResource(R.drawable.fatal_error)
                    tvDesc.text = resources.getString(R.string.connection_error)
                }
            }
            StateType.OPERATIONAL -> {
                binding.apply {
                    emptyStateRoot.visibility = View.VISIBLE
                    btnRetry.visibility = View.GONE
                    ivState.setImageResource(R.drawable.fatal_error)
                    tvDesc.text = resources.getString(R.string.operational_error_message)
                }
            }
            StateType.EMPTY -> {
                binding.apply {
                    emptyStateRoot.visibility = View.VISIBLE
                    btnRetry.visibility = View.GONE
                    ivState.setImageResource(R.drawable.empty_list)
                    tvDesc.text = resources.getString(R.string.empty_error)
                }
            }
        }
    }

    enum class StateType {
        NO_ERROR, CONNECTION, OPERATIONAL, EMPTY
    }
}