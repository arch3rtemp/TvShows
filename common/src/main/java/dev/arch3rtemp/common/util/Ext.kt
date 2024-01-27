package dev.arch3rtemp.common.util

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