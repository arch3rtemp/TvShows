package dev.arch3rtemp.common_data.exception

import dev.arch3rtemp.common.exception.ErrorMapper
import dev.arch3rtemp.common.exception.HttpErrorException
import dev.arch3rtemp.common.exception.NoInternetException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class DefaultErrorMapper @Inject constructor() : ErrorMapper {
    override fun invoke(exception: Throwable): Throwable = when (exception) {

        is HttpException -> HttpErrorException(exception.code(), exception.message(), exception.cause)

        is UnknownHostException, is SocketTimeoutException, is ConnectException ->
            NoInternetException(exception.localizedMessage, exception.cause)

        else -> exception
    }
}