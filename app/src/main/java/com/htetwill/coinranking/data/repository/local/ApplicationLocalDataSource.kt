package com.htetwill.coinranking.data.repository.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.htetwill.coinranking.common.CustomDatabase
import com.htetwill.coinranking.data.dao.CoinDao
import com.htetwill.coinranking.data.event.ErrorEvent
import com.htetwill.coinranking.data.event.Event
import com.htetwill.coinranking.data.event.EventHandler
import com.htetwill.coinranking.data.event.SuccessEvent
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.modal.CoinRankingModel
import com.htetwill.coinranking.data.repository.ApplicationDataSource
import com.htetwill.coinranking.error.ExceptionError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ApplicationLocalDataSource  @Inject constructor(
    private val mDatabase: CustomDatabase,
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
    private val PAGED_LIST_PAGE_SIZE = 20

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