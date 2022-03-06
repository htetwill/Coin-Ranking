package com.htetwill.coinranking.common.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.repository.ApplicationRepository
import com.htetwill.coinranking.data.usecase.FetchAndSaveDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomViewModel @Inject constructor(
    private val mFetchAndSaveDataUseCase: FetchAndSaveDataUseCase,
    private val mRepository: ApplicationRepository
) : ViewModel() {

    private var _fetchTrigger = MutableLiveData<EventHandler<Unit>>()
    val fetchLiveData: LiveData<EventHandler<Unit>> = _fetchTrigger
    val isLoading = ObservableBoolean()

    private val _getAllDataTrigger= MutableLiveData<String>()
    val getDataLiveData: LiveData<EventHandler<PagedList<CoinModel>>> = Transformations.switchMap(_getAllDataTrigger) {
        mRepository.getData()
    }

    fun getAllData(){
        _getAllDataTrigger.value =""
    }


    fun fetchData() {
        _fetchTrigger.postValue(EventHandler.loading())
        CoroutineScope(Dispatchers.IO).launch {
            _fetchTrigger.postValue(EventHandler(mFetchAndSaveDataUseCase.invoke()))
        }
    }
}