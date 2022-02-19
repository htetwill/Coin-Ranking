package com.example.android.data.repository

import com.example.android.data.event.Event
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.modal.DataModel
import com.example.android.recyclerview.PostModel

interface ApplicationDataSource {
    suspend fun fetchPost() : Event<PostModel>
    suspend fun fetchData() : Event<CoinRankingModel>
}