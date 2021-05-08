package com.example.android.recyclerview

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1)
abstract class CarDatabase : RoomDatabase(){
    abstract fun articleDao() : ArticleDao
}