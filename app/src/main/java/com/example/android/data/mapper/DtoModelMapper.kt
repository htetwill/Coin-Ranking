package com.example.android.data.mapper

import com.example.android.data.response.PostResponse
import com.example.android.data.util.mapTo
import com.example.android.recyclerview.PostModel

fun PostResponse.mapToPostModel(): PostModel= mapTo<PostModel>().copy()