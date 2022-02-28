package com.htetwill.coinranking.data.modal

data class StatsModel(
    val total: Long,
    val totalCoins: Long,
    val totalMarkets: Long,
    val totalExchanges: Long,
    val totalMarketCap: String,
    val total24hVolume: String
)