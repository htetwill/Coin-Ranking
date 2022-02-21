package com.example.android.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android.data.modal.CoinModel

/**
 * Provide views to RecyclerView with data from LiveData.
 *
 */
class CoinListAdapter : ListAdapter<CoinModel,CoinViewHolder>(object : DiffUtil
.ItemCallback<CoinModel>() {
    override fun areItemsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: CoinModel, newItem: CoinModel): Boolean {
        return oldItem == newItem
    }
})
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        return CoinViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.setValue(getItem(position))
    }

    private var mItems = mutableListOf<CoinModel>()

    fun updateList(list: List<CoinModel>) {
        mItems.clear()
        mItems.addAll(list)
        submitList(list)
    }

}
