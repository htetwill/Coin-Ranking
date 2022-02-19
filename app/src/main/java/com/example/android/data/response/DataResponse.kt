package com.example.android.data.response

import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.mapper.mapToDataModel
import com.example.android.data.modal.CoinModel
import com.example.android.data.modal.DataModel
import com.example.android.data.modal.StatsModel

data class DataResponse(
    val stats: StatsResponse,
    val coins: List<CoinResponse>
) : IDtoModelMapper<DataResponse, DataModel> {
    override fun map(value: DataResponse): DataModel {
        return value.mapToDataModel()
    }
}
