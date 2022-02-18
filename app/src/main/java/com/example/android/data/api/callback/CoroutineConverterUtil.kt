package com.example.android.data.api.callback

import com.example.android.error.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter

object CoroutineConverterUtil {
    fun convert(converter: Converter<ResponseBody, ErrorResponse>, body: ResponseBody?): Any? {
        if (body == null || body.contentLength() == 0L) {
            return null
        }
        return try {
            converter.convert(body)
        } catch (e: Exception) {
            throw e
        }
    }
}