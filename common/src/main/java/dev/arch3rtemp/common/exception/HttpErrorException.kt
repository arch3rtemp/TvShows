package dev.arch3rtemp.common.exception

data class HttpErrorException(
    val code: Int,
    override val message: String?,
    override val cause: Throwable?
) : Exception()