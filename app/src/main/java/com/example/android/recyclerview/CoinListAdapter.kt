package com.example.android.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.data.modal.CoinModel
import com.example.android.recyclerview.databinding.ListItemCoinBinding

class CoinListAdapter ( val mListItemEvent: (ListItemEvent) -> Unit): ListAdapter<CoinModel,RecyclerView.ViewHolder>(object : DiffUtil
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
            return CoinViewHolder(ListItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false), mListItemEvent)
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
