package com.example.android.data.usecase

import android.util.Log
import com.example.android.data.event.ErrorEvent
import com.example.android.data.event.Event
import com.example.android.data.event.SuccessEvent
import com.example.android.data.repository.ApplicationRepository
import com.example.android.recyclerview.ResultOf
import javax.inject.Inject

class FetchAndSaveDataUseCase @Inject constructor(
    private val mApplicationRepository: ApplicationRepository) {
    suspend fun invoke(value: Unit): Event<Unit> {
        when(val fetchPostResponse = mApplicationRepository.fetchPost()) {
            is SuccessEvent -> {
                fetchPostResponse.data.run {
                    Log.wtf("", this.toString())
                }
            }
            is ErrorEvent -> {
                return ErrorEvent(fetchPostResponse.error)
            }
        }
        return SuccessEvent(Unit)
    }
}