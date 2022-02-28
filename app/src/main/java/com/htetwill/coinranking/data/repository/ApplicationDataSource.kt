package com.htetwill.coinranking.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.modal.CoinRankingModel

interface ApplicationDataSource {
    suspend fun clearDatabase(): Event<Unit>
    suspend fun fetchData() : Event<CoinRankingModel>
    suspend fun saveData(list : List<CoinModel>) :Event<List<Long>>
    suspend fun deleteData(): Event<Int>
    fun getData() : LiveData<EventHandler<PagedList<CoinModel>>>
}