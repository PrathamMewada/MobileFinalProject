package com.group8.mobilefinalproject.data.remote

/**
 * Represents the state of a network request.
 * Loading: request is in progress.
 * Success: request completed and contains data.
 * Error: request failed and contains a user-readable message.
 */
sealed class ApiResult<out T> {
    data object Loading : ApiResult<Nothing>()
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val message: String, val cause: Throwable? = null) : ApiResult<Nothing>()
}
