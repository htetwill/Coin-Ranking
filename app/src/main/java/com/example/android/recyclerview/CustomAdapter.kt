package com.example.android.recyclerview

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

/**
 * Provide views to RecyclerView with data from LiveData.
 *
 */
class CustomAdapter : PagedListAdapter<Article,CoinViewHolder>(object : DiffUtil
.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
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
}
