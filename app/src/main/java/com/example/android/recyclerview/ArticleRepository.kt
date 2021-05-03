package com.example.android.recyclerview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.lang.Exception

class ArticleRepository private constructor(private val dao : ArticleDao){

    fun save(articles: List<Article>){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertAll(articles)
        }
    }
    companion object {
        // For Singleton instantiation
        @Volatile private var instance: ArticleRepository? = null

        fun getInstance(dao: ArticleDao) =
                instance ?: synchronized(this) {
                    instance ?: ArticleRepository(dao).also { instance = it }
                }
    }
}