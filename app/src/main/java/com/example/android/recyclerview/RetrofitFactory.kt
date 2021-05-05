package com.example.android.recyclerview

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object RetrofitFactory {
    const val BASE_URL = "https://www.apphusetreach.no/"

    fun makeRetrofitService(): RetrofitInterface {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(
                        MoshiConverterFactory.create(Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).build())
                )
                .build().create(RetrofitInterface::class.java)
    }
}