package com.example.android.recyclerview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.android.data.event.EventHandler
import com.example.android.data.modal.CoinModel
import com.example.android.data.repository.ApplicationRepository
import com.example.android.data.usecase.FetchAndSaveDataUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class V2RecyclerViewFragmentViewModel @Inject constructor(
    private val mFetchAndSaveDataUseCase: FetchAndSaveDataUseCase,
    private val mRepository: ApplicationRepository
) : ViewModel() {

    private var _fetchTrigger = MutableLiveData<EventHandler<Unit>>()
    val fetchLiveData: LiveData<EventHandler<Unit>> = _fetchTrigger

    private val _getAllDataTrigger= MutableLiveData<String>()
    val getDataLiveData: LiveData<EventHandler<PagedList<CoinModel>>> = Transformations.switchMap(_getAllDataTrigger) {
        mRepository.getData()
    }

    fun getAllData(){
        _getAllDataTrigger.value =""
    }


    fun fetchData() {
        // // TODO: 2022-02-15,23:37 htetwill work in progress
        _fetchTrigger.postValue(EventHandler.loading())
        CoroutineScope(Dispatchers.IO).launch {
            _fetchTrigger.postValue(EventHandler(mFetchAndSaveDataUseCase.invoke(Unit)))
        }
    }
}