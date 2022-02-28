package com.htetwill.coinranking.data.api

import com.htetwill.coinranking.data.response.CoinRankingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CustomApi {
    @GET("coins")
    @Headers("Cache-Control: no-cache")
    suspend fun fetchData(): Response<CoinRankingResponse>
}