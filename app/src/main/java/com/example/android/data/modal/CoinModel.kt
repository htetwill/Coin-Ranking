package com.example.android.data.modal

data class CoinModel(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String? = null,
    val iconURL: String,
    val marketCap: String,
    val price: String,
    val listedAt: Long,
    val tier: Long,
    val change: String,
    val rank: Long,
    val sparkline: List<String>,
    val lowVolume: Boolean,
    val coinrankingURL: String,
    val the24hVolume: String,
    val btcPrice: String
)