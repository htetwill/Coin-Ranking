package com.example.android.recyclerview

import com.example.android.data.modal.CoinModel

sealed class ListItemEvent {
    data class ItemClicked(val mCoinModel: CoinModel): ListItemEvent()
}
