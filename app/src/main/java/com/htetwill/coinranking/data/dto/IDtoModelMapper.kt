package com.htetwill.coinranking.data.dto

interface IDtoModelMapper<T,F> {
    fun map(value: T): F
}