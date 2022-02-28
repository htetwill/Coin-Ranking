package com.htetwill.coinranking.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.htetwill.coinranking.data.api.CustomApi
import com.htetwill.coinranking.data.api.callback.CoroutineAdapter
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.modal.CoinRankingModel
import com.htetwill.coinranking.data.repository.ApplicationDataSource
import retrofit2.Retrofit
import javax.inject.Inject

class ApplicationRemoteDataSource @Inject constructor(
    private val mCustomApi: CustomApi,
    private val mRetrofit: Retrofit
) : ApplicationDataSource {

    override suspend fun fetchData(): Event<CoinRankingModel> =
        CoroutineAdapter(mCustomApi.fetchData(), mRetrofit)()

    override suspend fun clearDatabase(): Event<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun saveData(list: List<CoinModel>): Event<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteData(): Event<Int> {
        TODO("Not yet implemented")
    }

    override fun getData(): LiveData<EventHandler<PagedList<CoinModel>>> {
        TODO("Not yet implemented")
    }
}