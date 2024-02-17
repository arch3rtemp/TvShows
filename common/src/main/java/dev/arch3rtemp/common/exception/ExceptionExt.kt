package dev.arch3rtemp.common.exception

inline fun <T> Result<T>.mapError(transformer: (exception: Throwable) -> Throwable): Result<T> {
    return when {
        isSuccess -> Result.success(getOrThrow())
        else -> Result.failure(transformer(exceptionOrNull()!!))
    }
}