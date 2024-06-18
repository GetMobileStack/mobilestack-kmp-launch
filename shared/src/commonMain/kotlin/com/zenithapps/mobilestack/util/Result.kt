package com.zenithapps.mobilestack.util

sealed class Result<out Data, out Error> {
    data class Success<Data>(val data: Data) : Result<Data, Nothing>()
    class Error<Error>(val error: Error) : Result<Nothing, Error>()
}