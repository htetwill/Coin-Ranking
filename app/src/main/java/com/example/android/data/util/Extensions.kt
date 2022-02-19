package com.example.android.data.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
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
