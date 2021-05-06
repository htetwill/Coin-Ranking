package com.example.android.recyclerview

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitFactory {
    const val BASE_URL = "https://www.apphusetreach.no/"

    fun makeRetrofitService(): RetrofitInterface {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RetrofitInterface::class.java)
    }
}