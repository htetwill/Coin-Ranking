package com.example.android.data.repository

import com.example.android.data.event.Event
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.modal.DataModel
import com.example.android.di.annotation.Remote
import com.example.android.recyclerview.PostModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
    @Remote private val mRemoteApplicationDataSource: ApplicationDataSource
) : ApplicationDataSource{
    override suspend fun fetchPost(): Event<PostModel> = mRemoteApplicationDataSource.fetchPost()
    override suspend fun fetchData(): Event<CoinRankingModel> = mRemoteApplicationDataSource.fetchData()
}