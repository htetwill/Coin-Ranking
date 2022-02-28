package com.htetwill.coinranking.fragment

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.htetwill.coinranking.data.event.ErrorEvent
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.event.LoadingEvent
import com.htetwill.coinranking.data.event.SuccessEvent
import com.htetwill.coinranking.data.util.isNull
import com.htetwill.coinranking.error.CustomError

fun <T : Any, L : LiveData<EventHandler<T>>> Fragment.observePeek(
    liveData: L,
    onSuccess: (data: T) -> Unit,
    onError: ((error: CustomError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, false)
}


fun <T : Any, L : LiveData<EventHandler<T>>> Fragment.observeSingle(
    liveData: L,
    onSuccess: (data: T) -> Unit,
    onError: ((error: CustomError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null
) {
    observe(liveData, onSuccess, onError, onLoading, onHideLoading, true)
}

private fun <T : Any, L : LiveData<EventHandler<T>>> Fragment.observe(
    liveData: L,
    onSuccess: (data: T) -> Unit,
    onError: ((error: CustomError) -> Unit)? = null,
    onLoading: (() -> Unit)? = null,
    onHideLoading: (() -> Unit)? = null,
    isSingleEvent: Boolean
) {
    liveData.observe(this, Observer {
        when (it) {
            is EventHandler<T> -> {
                val eventHandler = if (isSingleEvent) it.getContentIfNotHandled() else it.peekContent()
                eventHandler?.let {
                    when (it.event) {
                        is SuccessEvent -> {
                            onHideLoading?.invoke()
                            onHideLoading.isNull { Log.wtf("ExtensionObserver", "SuccessEvent")}
                            it.event.data?.run{
                                onSuccess(this)
                            }
                        }

                        is ErrorEvent -> {
                            onHideLoading?.invoke()
                            onHideLoading.isNull { Log.wtf("ExtensionObserver","ErrorEvent") }
                            onError?.run {
                                invoke(it.event.error)
                            }.isNull {
                                Log.wtf("ExtensionObserver","ErrorEvent")
                            }
                        }
                        is LoadingEvent -> {
                            onLoading?.run {
                                invoke()
                            }.isNull {
                                Log.wtf("ExtensionObserver","LoadingEvent")
                            }
                        }
                    }
                }
            }
        }
    })
}
