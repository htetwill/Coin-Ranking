package com.htetwill.coinranking.data.event

import com.htetwill.coinranking.error.CustomError

/**
 * This class is used to wrap data from data layer to presentation layer
 */

sealed class Event<T>
data class DoneEvent<T>(val data: T?) : Event<T>()
class LoadingEvent<T> : Event<T>()
data class ErrorEvent<T>(val error: CustomError): Event<T>()

