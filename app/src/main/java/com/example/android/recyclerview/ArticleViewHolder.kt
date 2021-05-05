package com.example.android.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.recyclerview.databinding.ListItemArticleBinding
import com.squareup.picasso.Picasso
import android.text.format.DateFormat.is24HourFormat


class ArticleViewHolder constructor(val rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ListItemArticleBinding.bind(rootView)

    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
            return ArticleViewHolder(view)
        }
    }

    @SuppressLint("SetTextI18n")
    fun setValue(item: Article?) {
        if (item != null) {
            binding.txtTitle.text = item.title
            binding.textView7.text = item.ingress
            val use24HourClock: Boolean = is24HourFormat(rootView.context)
            val customDate = DateTimeConverter.toCustomDate(item.dateTime!!)
            val customTime = DateTimeConverter.toCustomTime(item.dateTime, use24HourClock)
            binding.txtDatetime.text = "$customDate, $customTime"
            Picasso.get().load(item.image).into(binding.imageArticle)

        }
    }

}
