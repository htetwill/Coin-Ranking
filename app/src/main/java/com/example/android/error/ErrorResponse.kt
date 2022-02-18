package com.example.android.error

data class ErrorResponse(
    var message: String?,
    var errorCode: String,
    val status: Int
)