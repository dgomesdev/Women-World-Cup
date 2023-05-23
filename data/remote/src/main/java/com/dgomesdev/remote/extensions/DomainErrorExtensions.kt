package com.dgomesdev.remote.extensions

import com.dgomesdev.remote.NotFoundException
import com.dgomesdev.remote.UnexpectedException
import java.net.HttpURLConnection
import retrofit2.HttpException as RetrofitHttpException

internal fun <T> Result<T>.getOrThrowDomainError(): T = getOrElse { throwable ->
    throw throwable.toDomainError()
}

internal fun Throwable.toDomainError(): Throwable {
    return when (this) {
        is RetrofitHttpException -> {
            when (code()) {
                HttpURLConnection.HTTP_NOT_FOUND ->
                    NotFoundException("Matches not found! :'(")
                else -> UnexpectedException()
            }
        }
        else -> this
    }
}