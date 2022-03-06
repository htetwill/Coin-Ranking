package com.htetwill.coinranking.data.event

import com.htetwill.coinranking.error.CustomError

/**
 * This class is used to wrap data from data layer to presentation layer and detect if object already handled
 */

data class EventHandler<out T>(val event: Event<out T>) {
    companion object {
        fun <T> done(data: T?): EventHandler<T> {
            return EventHandler(DoneEvent(data))
        }

        fun <T> error(error: CustomError): EventHandler<T> {
            return EventHandler(ErrorEvent(error))
        }

        fun <T> loading(): EventHandler<T> {
            return EventHandler(LoadingEvent())
        }
    }

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): EventHandler<T?>? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            EventHandler(event)
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): EventHandler<T?> = EventHandler(event)
}
