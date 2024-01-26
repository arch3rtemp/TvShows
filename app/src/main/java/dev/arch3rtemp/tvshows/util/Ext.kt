package dev.arch3rtemp.tvshows.util

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import dev.arch3rtemp.tvshows.R
import kotlin.math.round

fun Double.roundToTenth(): Double {
    return round(this * 10) / 10
}

fun formatErrorMessage(code: Int, message: String?): String {
    return "Error code: $code\nError Message: ${message ?: "Empty"}"
}

fun formatImageUrl(partialUrl: String): String {
    return "https://image.tmdb.org/t/p/w500$partialUrl"
}

fun Fragment.showSnackbar(message: String, status: SnackbarStatusCodes) {

    val color = when (status) {
        SnackbarStatusCodes.ERROR -> {
            R.color.material_color_red_A400
        }
        SnackbarStatusCodes.SUCCESS -> {
            R.color.material_color_light_green_A400
        }
        else -> {
            throw IllegalArgumentException("Wrong snackbar status!")
        }
    }

    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).apply {
        animationMode = Snackbar.ANIMATION_MODE_SLIDE
        view.setBackgroundColor(ContextCompat.getColor(requireActivity(), color))
        show()
    }
}