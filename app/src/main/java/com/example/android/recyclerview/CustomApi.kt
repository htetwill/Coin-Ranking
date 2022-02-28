package com.example.android.recyclerview

import com.example.android.data.response.CoinRankingResponse
import com.example.android.data.response.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CustomApi {
    @GET("application/119267/article/get_articles_list")
    suspend fun fetchPost(): Response<PostResponse>

    @GET("coins")
    @Headers("Cache-Control: no-cache")
    suspend fun fetchData(): Response<CoinRankingResponse>
}