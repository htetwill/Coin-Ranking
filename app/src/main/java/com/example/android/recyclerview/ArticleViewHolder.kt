package com.example.android.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.recyclerview.databinding.ListItemArticleBinding
import com.squareup.picasso.Picasso

class ArticleViewHolder constructor(val rootView: View): RecyclerView.ViewHolder(rootView) {
    private val binding = ListItemArticleBinding.bind(rootView)

    companion object {
        fun from(parent: ViewGroup): ArticleViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
            return ArticleViewHolder(view)
        }
    }

    fun setValue(item: Article?) {
        if (item != null) {
            binding.txtTitle.text = item.title
            Picasso.get().load(item.image).into(binding.imageArticle)

        }
    }

}
