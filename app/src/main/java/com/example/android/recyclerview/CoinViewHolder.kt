package com.example.android.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.example.android.data.modal.CoinModel
import com.example.android.data.util.isPositive
import com.example.android.data.util.load
import com.example.android.recyclerview.databinding.ListItemCoinBinding


sealed class ListItemHolder(
    rootView: View,
) : RecyclerView.ViewHolder(rootView) {
    abstract fun bind(mCoinModel: CoinModel)
    abstract fun getCoinModel(): CoinModel
}

class CoinViewHolder(
    private val binding: ListItemCoinBinding,
    private val mImageLoader: ImageLoader,
    private val mListItemEvent: (ListItemEvent) -> Unit
) : ListItemHolder(
    binding.root,
) {

    private lateinit var mSelectedCoin: CoinModel

    override fun bind(mCoinModel: CoinModel) {
        initListeners()

        mSelectedCoin = mCoinModel
        binding.tvName.text = mCoinModel.name
        binding.tvSymbol.text = mCoinModel.symbol
        binding.tvPrice.text = mCoinModel.price
        if(mCoinModel.change.isPositive()) {
            binding.imgIndicator.setImageResource(R.drawable.ic_arrow_upward)
        } else {
            binding.imgIndicator.setImageResource(R.drawable.ic_arrow_downward)
        }
        binding.tvChange.text = mCoinModel.change.trim('-', '+')
        binding.imgIcon.load(mCoinModel.iconUrl, mImageLoader) {
            placeholder(R.drawable.ic_baseline_sync_24)
            error(R.drawable.ic_baseline_error_outline_24)
        }
    }

    private fun initListeners() {
        itemView.setOnClickListener {
            mListItemEvent.invoke(ListItemEvent.ItemClicked(mSelectedCoin))
        }
    }

    override fun getCoinModel(): CoinModel = mSelectedCoin
}
