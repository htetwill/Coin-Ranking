package com.htetwill.coinranking.data.response

import com.htetwill.coinranking.data.dto.IDtoModelMapper
import com.htetwill.coinranking.data.mapper.mapToCoinRankingModel
import com.htetwill.coinranking.data.modal.CoinRankingModel

data class CoinRankingResponse(
    val status : String,
    val data: DataResponse
) : IDtoModelMapper<CoinRankingResponse, CoinRankingModel> {
    override fun map(value: CoinRankingResponse): CoinRankingModel {
        return value.mapToCoinRankingModel()
    }
}