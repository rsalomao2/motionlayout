package com.dummy.domain

sealed class Status<out T> {
    class Success<T>(val response: T) : Status<T>()
    class Error(val responseError: String) : Status<Nothing>()
}

fun <T> Status<T>.apply(onSuccess: (T) -> Unit, onError: (String) -> Unit) {
    when (this) {
        is Status.Success -> onSuccess.invoke(this.response)
        is Status.Error -> onError.invoke(this.responseError)
    }
}
