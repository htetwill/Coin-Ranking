package com.example.android.recyclerview

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.data.modal.CoinModel

@Database(entities = [CoinModel::class], version = 1, exportSchema = false)
abstract class CustomDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}