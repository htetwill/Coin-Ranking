package com.htetwill.coinranking.data.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.GsonBuilder


inline fun <reified T : Any> Any.mapTo(): T =
    GsonBuilder().create().run {
        toJson(this@mapTo).let { fromJson(it, T::class.java) }
    }

fun Any.toJson(): String = GsonBuilder().create().toJson(this@toJson)

inline fun <reified T : Any> String.fromJson(): T =
    GsonBuilder().create().fromJson(this, T::class.java)

inline fun <reified T : ViewModel> Fragment.viewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(this, factory).get(T::class.java)
}

fun Any?.isNull(onNull: () -> Unit) {
    if (this == null) {
        onNull.invoke()
    }
}

fun String.isPositive(): Boolean {
    val value: Double = this.toDouble()
    return value >= 0
}

fun Context.isConnectedToNetwork(): Boolean {
    try {
        val connectivityManager = applicationContext?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetworkInfo
            return activeNetwork != null && activeNetwork.isConnected
        }
        return false
    } catch (e: Exception) {
        return false
    }
}