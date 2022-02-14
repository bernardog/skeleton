package com.bernardoghazi.skeleton.domain.models

import com.google.gson.annotations.SerializedName

sealed class Content

data class Header(
    val date: String
) : Content()

data class Post(
    val author: Author,
    val date: String,
    val excerpt: String,
    @SerializedName("featured_image") val featuredImage: String,
    val title: String,
    val url: String
) : Content()

object Divider : Content()

data class Author(
    val url: String,
    val name: String
)