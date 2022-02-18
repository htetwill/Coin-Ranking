package com.example.android.data.api.callback

import com.example.android.data.api.callback.CoroutineConverterUtil.convert
import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.event.ErrorEvent
import com.example.android.data.event.Event
import com.example.android.data.event.SuccessEvent
import com.example.android.error.BackendError
import com.example.android.error.CustomError
import com.example.android.error.ErrorResponse
import com.example.android.error.ExceptionError
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