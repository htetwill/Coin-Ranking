package com.example.android.data.repository

import com.example.android.data.event.Event
import com.example.android.recyclerview.PostModel

interface ApplicationDataSource {
    suspend fun fetchPost() : Event<PostModel>
}