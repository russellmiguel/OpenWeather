package com.robertrussell.miguel.openweather.model.api

sealed class Response<out T> {

    object Loading : Response<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : Response<T>()

    data class Failure<out T>(
        val e: Exception?
    ) : Response<T>()
}
