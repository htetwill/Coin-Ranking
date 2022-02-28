package com.htetwill.coinranking.data.modal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Coin")
data class CoinModel(
    @PrimaryKey val uuid: String,
    val symbol: String,
    val name: String,
    val color: String? = null,
    val iconUrl: String,
    val marketCap: String,
    val price: String,
    val listedAt: Long,
    val tier: Long,
    val change: String,
    val rank: Long,
    val lowVolume: Boolean,
    val coinrankingUrl: String,
    val btcPrice: String
)