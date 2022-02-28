package com.example.android.data.usecase

import android.util.Log
import com.example.android.data.event.ErrorEvent
import com.example.android.data.event.Event
import com.example.android.data.event.LoadingEvent
import com.example.android.data.event.SuccessEvent
import com.example.android.data.repository.ApplicationRepository
import javax.inject.Inject

class FetchAndSaveDataUseCase @Inject constructor(
    private val mApplicationRepository: ApplicationRepository) {
    suspend fun invoke(value: Unit): Event<Unit> {
        when(val fetchPostResponse = mApplicationRepository.fetchData()) {
            is SuccessEvent -> {
                mApplicationRepository.deleteData()
                fetchPostResponse.data.run {
                    when(val saveDataResponse = mApplicationRepository.saveData(this!!.data!!.coins)){
                        is ErrorEvent -> return ErrorEvent(saveDataResponse.error)
                        is LoadingEvent -> Log.wtf("fetchPostResponse","LoadingEvent")
                        is SuccessEvent -> Log.wtf("fetchPostResponse","SuccessEvent")
                    }
                }
            }
            is ErrorEvent -> return ErrorEvent(fetchPostResponse.error)
        }
        return SuccessEvent(Unit)
    }
}