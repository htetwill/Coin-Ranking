package com.htetwill.coinranking.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import com.htetwill.coinranking.data.event.ListItemEvent
import com.htetwill.coinranking.data.modal.CoinModel
import com.htetwill.coinranking.fragment.CoinViewHolder
import com.htetwill.coinranking.fragment.ListItemHolder
import com.htetwill.coinranking.fragment.databinding.ListItemCoinBinding

class CoinListAdapter(
    private val mImageLoader: ImageLoader,
    private val mListItemEvent:(ListItemEvent)-> Unit ): ListAdapter<CoinModel,RecyclerView.ViewHolder>(object : DiffUtil
.ItemCallback<CoinModel>() {
    override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }
})
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return CoinViewHolder(
                ListItemCoinBinding.inflate(LayoutInflater.from(parent.context),
                    parent,
                    false),
                mImageLoader,
                mListItemEvent
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position).let {
            val listItemHolder = holder as ListItemHolder
            listItemHolder.bind(it)
        }
    }

    private var mItems = mutableListOf<CoinModel>()

    fun updateList(list: List<CoinModel>) {
        mItems.clear()
        mItems.addAll(list)
        submitList(list)
    }
}
