package dev.arch3rtemp.common_ui.util

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dev.arch3rtemp.common.exception.NoInternetException
import dev.arch3rtemp.common_ui.R
import dev.arch3rtemp.common_ui.customview.EmptyView

fun Fragment.showSnackbar(message: String, status: SnackbarStatusCodes) {

    val color = when (status) {
        SnackbarStatusCodes.ERROR -> {
            R.color.material_color_red_A400
        }
        SnackbarStatusCodes.SUCCESS -> {
            R.color.material_color_light_green_A400
        }
    }

    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).apply {
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), color))
        show()
    }
}
enum class SnackbarStatusCodes {
    ERROR, SUCCESS
}

fun checkError(e: Throwable): EmptyView.StateType {
    return if (e is NoInternetException) {
        EmptyView.StateType.CONNECTION
    } else {
        EmptyView.StateType.OPERATIONAL
    }
}