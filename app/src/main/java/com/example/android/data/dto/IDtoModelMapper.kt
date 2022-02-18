package com.example.android.data.dto

interface IDtoModelMapper<T,F> {
    fun map(value: T): F
}