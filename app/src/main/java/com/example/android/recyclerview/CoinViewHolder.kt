package com.example.android.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.android.data.modal.CoinModel
import com.example.android.data.util.isPositive
import com.example.android.recyclerview.databinding.ListItemCoinBinding
import com.squareup.picasso.Picasso


sealed class ListItemHolder (
    rootView: View,
    val mListItemEvent: (ListItemEvent) -> Unit
): RecyclerView.ViewHolder(rootView) {
    abstract fun bind(mCoinModel: CoinModel)
    abstract fun getCoinModel(): CoinModel
}
class CoinViewHolder(
    private val binding: ListItemCoinBinding,
    mListItemEvent: (ListItemEvent) -> Unit
) : ListItemHolder(binding.root, mListItemEvent) {

    private lateinit var mSelectedCoin: CoinModel

    override fun bind(mCoin: CoinModel) {
        mSelectedCoin = mCoin
        binding.tvName.text = mCoin.name
        binding.tvSymbol.text = mCoin.symbol
        binding.tvPrice.text = mCoin.price
        if(mCoin.change.isPositive()) {
            binding.imgIndicator.setImageResource(R.drawable.ic_arrow_upward)
        } else {
            binding.imgIndicator.setImageResource(R.drawable.ic_arrow_downward)
        }
        binding.tvChange.text = mCoin.change.trim('-', '+')
        Picasso.get()
            .load(mCoin.iconUrl)
            .placeholder(R.drawable.ic_baseline_sync_24)
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(binding.imgIcon)
        initListeners()
    }

    private fun initListeners() {
        itemView.setOnClickListener {
            mListItemEvent.invoke(ListItemEvent.ItemClicked(mSelectedCoin))
        }
    }

    override fun getCoinModel(): CoinModel = mSelectedCoin
}
