package com.htetwill.coinranking.data.api.callback

import com.htetwill.coinranking.error.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit

class ErrorConverter(private val retrofit: Retrofit) : Converter<ResponseBody, ErrorResponse> {
    override fun convert(value: ResponseBody): ErrorResponse? {
        val retrofitConverter: Converter<ResponseBody, ErrorResponse> =
            retrofit.responseBodyConverter(ErrorResponse::class.java, arrayOfNulls<Annotation>(0))
        return try {
            retrofitConverter.convert(value)
        } catch (e: Exception) {
            throw e
        }
    }
}