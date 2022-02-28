package com.htetwill.coinranking.error

data class ErrorResponse(
    var message: String?,
    var errorCode: String,
    val status: Int
)