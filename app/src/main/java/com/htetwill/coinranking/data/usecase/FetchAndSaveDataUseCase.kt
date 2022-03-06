package com.htetwill.coinranking.data.usecase

import android.content.Context
import android.util.Log
import com.htetwill.coinranking.data.event.DoneEvent
import com.htetwill.coinranking.data.event.ErrorEvent
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.LoadingEvent
import com.htetwill.coinranking.data.repository.ApplicationRepository
import com.htetwill.coinranking.data.util.isConnectedToNetwork
import com.htetwill.coinranking.error.NoInternetConnectionError
import javax.inject.Inject

class FetchAndSaveDataUseCase @Inject constructor(
    private val mApplicationRepository: ApplicationRepository,
    private val mContext: Context
) {
    suspend fun invoke(): Event<Unit> {
        return try {
            if(mContext.isConnectedToNetwork()) {
                when(val fetchPostResponse = mApplicationRepository.fetchData()) {
                    is DoneEvent -> {
                        mApplicationRepository.deleteData()
                        fetchPostResponse.data.run {
                            when(val saveDataResponse =
                                mApplicationRepository.saveData(this!!.data!!.coins)) {
                                is ErrorEvent -> return ErrorEvent(saveDataResponse.error)
                                is LoadingEvent -> Log.wtf("fetchPostResponse", "LoadingEvent")
                                is DoneEvent -> Log.wtf("fetchPostResponse", "DoneEvent")
                            }
                        }
                    }
                    is ErrorEvent -> return ErrorEvent(fetchPostResponse.error)
                }
                DoneEvent(Unit)
            }else{
                ErrorEvent(NoInternetConnectionError)
            }
        } catch(e: Exception) {
            return DoneEvent(Unit)
        }
    }
}