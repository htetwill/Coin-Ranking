package com.example.android.data.response

import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.mapper.mapToCoinModel
import com.example.android.data.modal.CoinModel
import com.google.gson.annotations.SerializedName

data class CoinResponse(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String? = null,

    @SerializedName("iconUrl")
    val iconURL: String,

    val marketCap: String,
    val price: String,
    val listedAt: Long,
    val tier: Long,
    val change: String,
    val rank: Long,
    val sparkline: List<String>,
    val lowVolume: Boolean,

    @SerializedName("coinrankingUrl")
    val coinrankingURL: String,

    @SerializedName("24hVolume")
    val the24hVolume: String,

    val btcPrice: String
): IDtoModelMapper<CoinResponse, CoinModel> {
    override fun map(value: CoinResponse): CoinModel {
        return value.mapToCoinModel()
    }
}
