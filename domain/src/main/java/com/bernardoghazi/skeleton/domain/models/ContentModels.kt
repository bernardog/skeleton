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
    val description: String,
    val mediaUrl: String?,
    val byline: String
) : Content()

object Divider : Content()