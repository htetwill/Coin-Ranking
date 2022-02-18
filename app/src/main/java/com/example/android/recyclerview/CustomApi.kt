package com.example.android.recyclerview

import com.example.android.data.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface CustomApi {
    @GET("application/119267/article/get_articles_list")
    suspend fun fetchPost(): Response<PostResponse>
}