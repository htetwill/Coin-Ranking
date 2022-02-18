package com.example.android.data.event

import com.example.android.error.CustomError

/**
 * This class is used to wrap data from data layer to presentation layer
 */

sealed class Event<T>
data class SuccessEvent<T>(val data: T?) : Event<T>()
class LoadingEvent<T> : Event<T>()
data class ErrorEvent<T>(val error: CustomError): Event<T>()

