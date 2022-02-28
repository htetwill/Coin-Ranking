package com.example.android.recyclerview

import com.example.android.data.response.CoinRankingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CustomApi {
    @GET("coins")
    @Headers("Cache-Control: no-cache")
    suspend fun fetchData(): Response<CoinRankingResponse>
}