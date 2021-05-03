package com.example.android.recyclerview

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(article: List<Article>): List<Long>

    

    @Query("SELECT * FROM Article")
    suspend fun getAll(): List<Article>

    @Query("SELECT * FROM Article WHERE created = (SELECT MAX(created) FROM Article )")
    suspend fun lastArticle(): Article
}