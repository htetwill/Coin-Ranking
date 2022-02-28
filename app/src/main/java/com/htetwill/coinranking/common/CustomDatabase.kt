package com.htetwill.coinranking.common

import androidx.room.Database
import androidx.room.RoomDatabase
import com.htetwill.coinranking.data.dao.CoinDao
import com.htetwill.coinranking.data.modal.CoinModel

@Database(entities = [CoinModel::class], version = 1, exportSchema = false)
abstract class CustomDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}