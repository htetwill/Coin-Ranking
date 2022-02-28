package com.htetwill.coinranking.data.usecase

import android.util.Log
import com.htetwill.coinranking.data.event.ErrorEvent
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.LoadingEvent
import com.htetwill.coinranking.data.event.SuccessEvent
import com.htetwill.coinranking.data.repository.ApplicationRepository
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