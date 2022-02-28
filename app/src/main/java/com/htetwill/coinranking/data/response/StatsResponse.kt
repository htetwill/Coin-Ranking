package com.htetwill.coinranking.data.response

import com.htetwill.coinranking.data.dto.IDtoModelMapper
import com.htetwill.coinranking.data.mapper.mapToStatsModel
import com.htetwill.coinranking.data.modal.StatsModel

data class StatsResponse(
    val total: Long,
    val totalCoins: Long,
    val totalMarkets: Long,
    val totalExchanges: Long,
    val totalMarketCap: String,
    val total24hVolume: String
) : IDtoModelMapper<StatsResponse, StatsModel> {
    override fun map(value: StatsResponse): StatsModel {
        return value.mapToStatsModel()
    }
}
