package com.htetwill.coinranking.di

import android.app.Application
import androidx.room.Room
import coil.ImageLoader
import coil.decode.SvgDecoder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.htetwill.coinranking.common.CustomDatabase
import com.htetwill.coinranking.data.api.CustomApi
import com.htetwill.coinranking.data.dao.CoinDao
import com.htetwill.coinranking.data.repository.ApplicationDataSource
import com.htetwill.coinranking.data.repository.local.ApplicationLocalDataSource
import com.htetwill.coinranking.data.repository.remote.ApplicationRemoteDataSource
import com.htetwill.coinranking.di.annotation.Local
import com.htetwill.coinranking.di.annotation.Remote
import com.htetwill.coinranking.fragment.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        application: Application,
    ): ImageLoader {
        return ImageLoader.Builder(application)
            .crossfade(true)
            .componentRegistry { add(SvgDecoder(application)) }
            .crossfade(500)
            .build()
    }


    @Provides
    @Singleton
    fun provideHttpCatche(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?): OkHttpClient {
        val interceptor : HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.apply {
            addInterceptor(interceptor)
        }
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
//        val baseUrl = "https://www.apphusetreach.no/"

        val baseUrl = "https://api.coinranking.com/v2/"
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
    fun bindApplicationRemoteDataSource(customApi: CustomApi, retrofit: Retrofit): ApplicationDataSource {
        return ApplicationRemoteDataSource(customApi, retrofit)
    }

    @Singleton
    @Provides
    @Local
    fun bindApplicationLocalDataSource(customDatabase: CustomDatabase, coinDao: CoinDao): ApplicationDataSource {
        return ApplicationLocalDataSource(customDatabase, coinDao)
    }

    @Singleton
    @Provides
    fun provideDatabase(application: Application): CustomDatabase {
        return Room.databaseBuilder(application, CustomDatabase::class.java, "custom.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinDao(mDatabase: CustomDatabase): CoinDao {
        return mDatabase.coinDao()
    }

}