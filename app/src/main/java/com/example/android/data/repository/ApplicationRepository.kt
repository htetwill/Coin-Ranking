package com.example.android.data.repository

import com.example.android.data.event.Event
import com.example.android.di.annotation.Remote
import com.example.android.recyclerview.PostModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationRepository @Inject constructor(
    @Remote private val mRemoteApplicationDataSource: ApplicationDataSource
) : ApplicationDataSource{
    override suspend fun fetchPost(): Event<PostModel> {
        return mRemoteApplicationDataSource.fetchPost()
    }
}