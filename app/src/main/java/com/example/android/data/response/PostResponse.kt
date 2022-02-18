package com.example.android.data.response

import com.example.android.data.dto.IDtoModelMapper
import com.example.android.data.mapper.mapToPostModel
import com.example.android.recyclerview.Article
import com.example.android.recyclerview.PostModel

data class PostResponse(
    val status: String?,
    val content: List<Article>?,
    val serverTime: String?
) : IDtoModelMapper<PostResponse, PostModel> {
    override fun map(value: PostResponse): PostModel {
        return value.mapToPostModel()
    }
}