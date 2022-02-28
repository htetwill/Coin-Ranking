package com.htetwill.coinranking.data.util

import android.widget.ImageView
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest

/**
 * Load the image referenced by [data] and set it on this [ImageView].
 */
inline fun ImageView.load(
    data: Any?,
    imageLoader: ImageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val request = ImageRequest.Builder(context)
        .apply(builder)
        .data(data)
        .target(this)
        .build()
    return imageLoader.enqueue(request)
}