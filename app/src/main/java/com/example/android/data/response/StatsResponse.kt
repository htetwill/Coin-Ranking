package com.example.android.data.response

import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.mapper.mapToStatsModel
import com.example.android.data.modal.StatsModel

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
