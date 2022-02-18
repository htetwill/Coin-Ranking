package com.example.android.data.repository.remote

import com.example.android.data.api.callback.CoroutineAdapter
import com.example.android.data.event.Event
import com.example.android.data.repository.ApplicationDataSource
import com.example.android.recyclerview.CustomApi
import com.example.android.recyclerview.PostModel
import retrofit2.Retrofit
import javax.inject.Inject

class ApplicationRemoteDataSource @Inject constructor(
    private val mCustomApi: CustomApi,
    private val mRetrofit: Retrofit
) : ApplicationDataSource {
    override suspend fun fetchPost(): Event<PostModel> {
        return CoroutineAdapter(mCustomApi.fetchPost(),mRetrofit)()
    }
}