package com.example.android.data.repository.remote

import com.example.android.data.api.callback.CoroutineAdapter
import com.example.android.data.event.Event
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.modal.DataModel
import com.example.android.data.repository.ApplicationDataSource
import com.example.android.recyclerview.CustomApi
import com.example.android.recyclerview.PostModel
import retrofit2.Retrofit
import javax.inject.Inject

class ApplicationRemoteDataSource @Inject constructor(
    private val mCustomApi: CustomApi,
    private val mRetrofit: Retrofit
) : ApplicationDataSource {
    override suspend fun fetchPost(): Event<PostModel> =
        CoroutineAdapter(mCustomApi.fetchPost(), mRetrofit)()

    override suspend fun fetchData(): Event<CoinRankingModel> =
        CoroutineAdapter(mCustomApi.fetchData(), mRetrofit)()
}