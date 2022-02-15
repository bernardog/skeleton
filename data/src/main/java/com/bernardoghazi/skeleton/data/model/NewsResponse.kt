package com.bernardoghazi.skeleton.data.model

import com.google.gson.annotations.SerializedName

data class MostPopularArticlesResponse(
    val status: String,
    val copyright: String,
    @SerializedName("num_results") val numResults: Int,
    @SerializedName("results") val articleResponses: List<ArticleResponse>
)

data class ArticleResponse(
    val url: String,
    @SerializedName("updated") val updatedAt: String,
    val section: String,
    val title: String,
    val abstract: String,
    val media: List<MediaResponse>
)

data class MediaResponse(
    @SerializedName("media-metadata") val mediaMetadata: List<MetadataResponse>
)

data class MetadataResponse(
    val url: String
)