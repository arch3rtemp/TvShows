package dev.arch3rtemp.common.exception

data class NoInternetException(override val message: String?, override val cause: Throwable?) : Exception()