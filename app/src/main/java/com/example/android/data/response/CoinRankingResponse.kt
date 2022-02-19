package com.example.android.data.response

import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.mapper.mapToCoinRankingModel
import com.example.android.data.modal.CoinRankingModel

data class CoinRankingResponse(
    val status : String,
    val data: DataResponse
) : IDtoModelMapper<CoinRankingResponse, CoinRankingModel> {
    override fun map(value: CoinRankingResponse): CoinRankingModel {
        return value.mapToCoinRankingModel()
    }
}