package dev.arch3rtemp.tvshows.common

sealed interface Resource<out T> {
    data class Success<T : Any>(val data: T) : Resource<T>
    data class Error(val code: Int, val message: String?) : Resource<Nothing>
    data class Exception(val e: Throwable) : Resource<Nothing>
}