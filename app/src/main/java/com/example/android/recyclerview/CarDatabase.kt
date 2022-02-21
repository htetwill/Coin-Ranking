package com.example.android.recyclerview

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.data.modal.CoinModel

@Database(entities = [Article::class, CoinModel::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun coinDao(): CoinDao
}