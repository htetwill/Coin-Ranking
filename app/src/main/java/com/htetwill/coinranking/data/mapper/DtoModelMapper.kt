package com.htetwill.coinranking.data.mapper

import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.data.modal.CoinRankingModel
import com.htetwill.coinranking.data.modal.DataModel
import com.htetwill.coinranking.data.modal.StatsModel
import com.htetwill.coinranking.data.response.CoinRankingResponse
import com.htetwill.coinranking.data.response.CoinResponse
import com.htetwill.coinranking.data.response.DataResponse
import com.htetwill.coinranking.data.response.StatsResponse
import com.htetwill.coinranking.data.util.mapTo

fun StatsResponse.mapToStatsModel(): StatsModel = mapTo<StatsModel>().copy()
fun DataResponse.mapToDataModel(): DataModel = mapTo<DataModel>().copy()
fun CoinResponse.mapToCoinModel(): CoinModel = mapTo<CoinModel>().copy()
fun CoinRankingResponse.mapToCoinRankingModel(): CoinRankingModel = mapTo<CoinRankingModel>().copy()
