package com.htetwill.coinranking.data.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.htetwill.coinranking.data.modal.CoinModel

@Dao
interface CoinDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(coinList: List<CoinModel>): List<Long>

    @Query("SELECT * FROM Coin ORDER BY rank ASC")
    fun getAll(): DataSource.Factory<Int, CoinModel>

    @Query("SELECT * FROM Coin")
    fun getAllInList(): List<CoinModel>

    @Query("DELETE FROM Coin")
    suspend fun deleteAll(): Int
}