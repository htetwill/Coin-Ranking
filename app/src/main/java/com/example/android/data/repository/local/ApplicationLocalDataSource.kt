package com.example.android.data.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.android.data.event.ErrorEvent
import com.example.android.data.event.Event
import com.example.android.data.event.EventHandler
import com.example.android.data.event.SuccessEvent
import com.example.android.data.modal.CoinModel
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.repository.ApplicationDataSource
import com.example.android.error.ExceptionError
import com.example.android.recyclerview.CarDatabase
import com.example.android.recyclerview.CoinDao
import com.example.android.recyclerview.PostModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ApplicationLocalDataSource  @Inject constructor(
    private val mDatabase: CarDatabase,
    private val mCoinDao: CoinDao
) : ApplicationDataSource, CoroutineScope{
    override val coroutineContext: CoroutineContext
        get() = Job()

    override suspend fun clearDatabase(): Event<Unit> {
        return try {
            SuccessEvent(mDatabase.clearAllTables())
        }catch (e: Exception){
            e.printStackTrace()
            ErrorEvent(ExceptionError(e))
        }
    }

    override suspend fun fetchPost(): Event<PostModel> {
        TODO("Not yet implemented")
    }

    override suspend fun fetchData(): Event<CoinRankingModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveData(list: List<CoinModel>): Event<List<Long>> {
        return try {
            SuccessEvent(mCoinDao.insertAll(list))
        } catch(e: Exception) {
            ErrorEvent(ExceptionError(e))
        }
    }

    override suspend fun deleteData(): Event<Int> {
        return try {
            SuccessEvent(mCoinDao.deleteAll())
        } catch (e: Exception) {
            ErrorEvent(ExceptionError(e))
        }
    }
    private val coinListLiveData = MutableLiveData<EventHandler<PagedList<CoinModel>>>()
    private val PAGED_LIST_PAGE_SIZE = 5

    override fun getData(): LiveData<EventHandler<PagedList<CoinModel>>> {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGED_LIST_PAGE_SIZE)
            .build()
        val livePagedListOrder = LivePagedListBuilder(
            mCoinDao.getAll().map { it },
            pagedListConfig
        ).build()
        return Transformations.switchMap(livePagedListOrder){
            coinListLiveData.postValue(EventHandler.success(it))
            return@switchMap coinListLiveData
        }
    }
}