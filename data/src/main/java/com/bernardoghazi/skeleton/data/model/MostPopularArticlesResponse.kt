package com.bernardoghazi.skeleton.data.model

import com.google.gson.annotations.SerializedName

data class MostPopularArticlesResponse(
    @SerializedName("results") val articleResponses: List<ArticleResponse>
)

data class ArticleResponse(
    val url: String,
    @SerializedName("updated") val updatedAt: String,
    val section: String,
    val title: String,
    @SerializedName("abstract") val description: String,
    val media: List<MediaResponse>,
    val byline: String
)

data class MediaResponse(
    @SerializedName("media-metadata") val mediaMetadata: List<MetadataResponse>
)

data class MetadataResponse(
    val url: String
)