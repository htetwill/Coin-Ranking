package com.htetwill.coinranking.data.api.callback

import com.htetwill.coinranking.data.api.callback.CoroutineConverterUtil.convert
import com.htetwill.coinranking.data.dto.IDtoModelMapper
import com.htetwill.coinranking.data.event.ErrorEvent
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.SuccessEvent
import com.htetwill.coinranking.error.BackendError
import com.htetwill.coinranking.error.ErrorResponse
import com.htetwill.coinranking.error.ExceptionError
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CoroutineAdapter<T : IDtoModelMapper<T, F>, F> @Inject constructor(
    private val response: Response<T>,
    private val retrofit: Retrofit
) {
    operator fun invoke(): Event<F> {
        if(response.code() == 401) {
            return ErrorEvent(ExceptionError(Exception()))
        }
        if(response.code() == 429) {
            return ErrorEvent(ExceptionError(Exception()))
        }
        return if(response.isSuccessful) {
            val responseBody = response.body()
            SuccessEvent(if(responseBody != null) response.body()?.map(responseBody) else null)
        } else {
            return try {
                val errorResponse =
                    convert(ErrorConverter(retrofit), response.errorBody()) as ErrorResponse
                ErrorEvent(BackendError(errorResponse))
            } catch(e: Exception) {
                ErrorEvent(ExceptionError(e))
            }
        }
    }
}