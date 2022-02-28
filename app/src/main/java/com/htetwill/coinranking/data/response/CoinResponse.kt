package com.htetwill.coinranking.data.response

import com.htetwill.coinranking.data.dto.IDtoModelMapper
import com.htetwill.coinranking.data.mapper.mapToCoinModel
import com.htetwill.coinranking.data.modal.CoinModel

data class CoinResponse(
    val uuid: String,
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
): IDtoModelMapper<CoinResponse, CoinModel> {
    override fun map(value: CoinResponse): CoinModel {
        return value.mapToCoinModel()
    }
}
