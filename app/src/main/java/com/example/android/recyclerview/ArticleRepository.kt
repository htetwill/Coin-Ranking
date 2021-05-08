package com.example.android.recyclerview

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleRepository private constructor(private val dao : ArticleDao){
    private val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(6)
            .setEnablePlaceholders(false)
            .build()

    fun save(articles: List<Article>){
        CoroutineScope(Dispatchers.IO).launch {
            dao.insertAll(articles)
        }
    }
    fun delete(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            dao.deleteItemById(id)
        }
    }

    fun getList(): LiveData<PagedList<Article>> {
        val factory: DataSource.Factory<Int, Article> = dao.getAll()
        return LivePagedListBuilder<Int, Article>(factory, pagedListConfig).build()

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