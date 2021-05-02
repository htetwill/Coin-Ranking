package com.example.android.recyclerview

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterface {
    @GET("application/119267/article/get_articles_list")
    suspend fun getPosts(): Response<Post>
}