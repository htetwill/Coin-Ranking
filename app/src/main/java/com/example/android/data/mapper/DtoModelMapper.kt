package com.example.android.data.mapper

import com.example.android.data.modal.DataModel
import com.example.android.data.modal.StatsModel
import com.example.android.data.modal.CoinModel
import com.example.android.data.modal.CoinRankingModel
import com.example.android.data.response.*
import com.example.android.data.util.mapTo
import com.example.android.recyclerview.PostModel

fun PostResponse.mapToPostModel(): PostModel = mapTo<PostModel>().copy()
fun StatsResponse.mapToStatsModel(): StatsModel = mapTo<StatsModel>().copy()
fun DataResponse.mapToDataModel(): DataModel = mapTo<DataModel>().copy()
fun CoinResponse.mapToCoinModel(): CoinModel = mapTo<CoinModel>().copy()
fun CoinRankingResponse.mapToCoinRankingModel(): CoinRankingModel = mapTo<CoinRankingModel>().copy()
