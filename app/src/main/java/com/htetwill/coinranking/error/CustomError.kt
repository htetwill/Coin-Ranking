package com.htetwill.coinranking.error

sealed class CustomError
data class ExceptionError(val exception: Exception) : CustomError()
data class BackendError(val error: ErrorResponse) : CustomError()