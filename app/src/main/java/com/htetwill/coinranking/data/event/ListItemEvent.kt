package com.htetwill.coinranking.data.event

import com.htetwill.coinranking.data.modal.CoinModel

sealed class ListItemEvent {
    data class ItemClicked(val mCoinModel: CoinModel): ListItemEvent()
}
