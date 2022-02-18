package com.example.android.di

import android.app.Application
import com.example.android.data.repository.ApplicationDataSource
import com.example.android.data.repository.remote.ApplicationRemoteDataSource
import com.example.android.di.annotation.CustomAnnotation
import com.example.android.di.annotation.Remote
import com.example.android.recyclerview.BuildConfig
import com.example.android.recyclerview.CustomApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideHttpCatche(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Singleton
    @Provides
    fun provideCustomApiRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return getRetrofit(BuildConfig.BASE_URL, gson, okHttpClient)
    }

    private fun getRetrofit(baseUrl: String, gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://www.apphusetreach.no/"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideCustomApiServices( retrofit: Retrofit): CustomApi {
        return retrofit.create<CustomApi>(CustomApi::class.java)
    }

    @Singleton
    @Provides
    @Remote
    fun bindApplicationRemoteDataSource(portierApi: CustomApi,  retrofit: Retrofit): ApplicationDataSource {
        return ApplicationRemoteDataSource(portierApi, retrofit)
    }
}