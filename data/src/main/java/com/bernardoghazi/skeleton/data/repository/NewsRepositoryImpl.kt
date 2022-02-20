package com.bernardoghazi.skeleton.data.repository

import com.bernardoghazi.skeleton.data.model.ArticleResponse
import com.bernardoghazi.skeleton.data.model.MostPopularArticlesResponse
import com.bernardoghazi.skeleton.data.remote.MostPopularService
import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository

class NewsRepositoryImpl(private val mostPopularService: MostPopularService) : NewsRepository {

    override suspend fun fetchMostPopularArticles(): List<Article>? {
        val mostPopularArticlesResponse: MostPopularArticlesResponse?
        try {
            mostPopularArticlesResponse = mostPopularService.fetchMostPopularArticles()
        } catch (e: Exception) {//TODO: deal with the error.
            return null
        }
        val articles: MutableList<Article> = mutableListOf()
        mostPopularArticlesResponse.articleResponses.forEach { articles.add(mapArticleResponseToArticle(it)) }
        return articles
    }

    /**Maps an [articleResponse] to an [Article].*/
    private fun mapArticleResponseToArticle(articleResponse: ArticleResponse): Article {
        with(articleResponse) {
            return Article(
                url = url,
                updatedAt = updatedAt,
                section = section,
                title = title,
                description = description,
                mediaUrl = if (media.isNotEmpty()) media[0].mediaMetadata[2].url else null,
                byline = byline
            )
        }
    }

}