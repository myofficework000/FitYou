package com.business.fityou.data.network

sealed class DataResult<out U, out V> {

    data class Success<out U>(val data: U) : DataResult<U, Nothing>()

    data class Error<out V>(val error: V) : DataResult<Nothing, V>()
}