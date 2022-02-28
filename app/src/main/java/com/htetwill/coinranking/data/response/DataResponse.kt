package com.htetwill.coinranking.data.response

import com.htetwill.coinranking.data.dto.IDtoModelMapper
import com.htetwill.coinranking.data.mapper.mapToDataModel
import com.htetwill.coinranking.data.modal.DataModel

data class DataResponse(
    val stats: StatsResponse,
    val coins: List<CoinResponse>
) : IDtoModelMapper<DataResponse, DataModel> {
    override fun map(value: DataResponse): DataModel {
        return value.mapToDataModel()
    }
}
