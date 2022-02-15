package com.bernardoghazi.skeleton.domain.models

sealed class Content

data class Header(
    val date: String
) : Content()

data class Article(
    val url: String,
    val updatedAt: String,
    val section: String,
    val title: String,
    val abstract: String,
    val mediaUrl: String
) : Content()

object Divider : Content()

data class Author(
    val url: String,
    val name: String
)