package com.example.android.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.data.modal.CoinModel
import com.example.android.recyclerview.databinding.ListItemCoinBinding


class CoinViewHolder constructor(private val rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ListItemCoinBinding.bind(rootView)

    companion object {
        fun from(parent: ViewGroup): CoinViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)

            val view = layoutInflater.inflate(R.layout.list_item_coin, parent, false)
            return CoinViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setValue(item: CoinModel?) {
        if (item != null) {
//            binding.txtTitle.text = item.title
//            binding.textView7.text = item.ingress
//            val use24HourClock: Boolean = is24HourFormat(rootView.context)
//            val customDate = DateTimeConverter.toCustomDate(item.dateTime!!)
//            val customTime = DateTimeConverter.toCustomTime(item.dateTime, use24HourClock)
//            binding.txtDatetime.text = "$customDate, $customTime"
//            Picasso.get()
//                    .load(item.image)
//                    .placeholder(R.drawable.ic_baseline_sync_24)
//                    .error(R.drawable.ic_baseline_error_outline_24)
//                    .into(binding.imageArticle)
        }
    }
}
