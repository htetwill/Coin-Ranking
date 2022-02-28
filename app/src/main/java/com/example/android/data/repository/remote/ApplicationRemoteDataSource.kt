package com.example.android.data.repository.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.android.data.api.callback.CoroutineAdapter
import com.example.android.data.event.Event
import com.example.android.data.event.EventHandler
import com.example.android.data.modal.CoinModel
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.repository.ApplicationDataSource
import com.example.android.recyclerview.CustomApi
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