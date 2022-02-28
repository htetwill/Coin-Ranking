package com.htetwill.coinranking.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.modal.CoinRankingModel
import com.htetwill.coinranking.di.annotation.Local
import com.htetwill.coinranking.di.annotation.Remote
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
    @Remote private val mRemoteApplicationDataSource: ApplicationDataSource,
    @Local private val mLocalApplicationDatasource: ApplicationDataSource
) : ApplicationDataSource {
    override suspend fun fetchData(): Event<CoinRankingModel> = mRemoteApplicationDataSource.fetchData()
    override suspend fun saveData(list: List<CoinModel>): Event<List<Long>> = mLocalApplicationDatasource.saveData(list)
    override suspend fun deleteData(): Event<Int> = mLocalApplicationDatasource.deleteData()
    override fun getData(): LiveData<EventHandler<PagedList<CoinModel>>> = mLocalApplicationDatasource.getData()
    override suspend fun clearDatabase(): Event<Unit> = mLocalApplicationDatasource.clearDatabase()
}

