package com.example.android.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.android.data.event.Event
import com.example.android.data.event.EventHandler
import com.example.android.data.modal.CoinModel
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.modal.DataModel
import com.example.android.recyclerview.PostModel

interface ApplicationDataSource {
    suspend fun clearDatabase(): Event<Unit>
    suspend fun fetchPost() : Event<PostModel>
    suspend fun fetchData() : Event<CoinRankingModel>
    suspend fun saveData(list : List<CoinModel>) :Event<List<Long>>
    suspend fun deleteData(): Event<Int>
    fun getData() : LiveData<EventHandler<PagedList<CoinModel>>>
}