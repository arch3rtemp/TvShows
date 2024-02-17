package dev.arch3rtemp.common.exception

interface ErrorMapper {
    operator fun invoke(exception: Throwable): Throwable
}