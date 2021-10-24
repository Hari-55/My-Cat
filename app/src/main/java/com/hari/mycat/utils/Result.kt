package com.hari.mycat.utils

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Error(var message: String) : Result<Nothing>()
    data class Data<T>(var data: T) : Result<T>()
}
