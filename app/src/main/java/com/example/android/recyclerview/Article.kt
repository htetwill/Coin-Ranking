package com.example.android.recyclerview

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Article (
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "dateTime") val dateTime: String?,
//    @ColumnInfo(name = "title") val content: List<Item>? = null
    @ColumnInfo(name = "ingress") val ingress: String?,
    @ColumnInfo(name = "image") val image: String?,

    @ColumnInfo(name = "created") val created : Int,
    @ColumnInfo(name = "changed") val changed : Int
//    val tags: List<Any>? = null
)
