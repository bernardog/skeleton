package com.bernardoghazi.skeleton.data.repository

import com.bernardoghazi.skeleton.data.model.ArticleResponse
import com.bernardoghazi.skeleton.data.model.MostPopularArticlesResponse
import com.bernardoghazi.skeleton.data.remote.NewsService
import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository

class NewsRepositoryImpl(private val newsService: NewsService) : NewsRepository {

    override suspend fun fetchMostPopularArticles(): List<Article>? {
        val mostPopularArticlesResponse: MostPopularArticlesResponse?
        try {
            mostPopularArticlesResponse = newsService.fetchMostPopularArticles()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val articles: MutableList<Article> = mutableListOf()
        mostPopularArticlesResponse.articleResponses.forEach { articles.add(mapArticleResponseToArticle(it)) }
        return articles
    }

    /**Maps [articleResponse] to an [Article].*/
    private fun mapArticleResponseToArticle(articleResponse: ArticleResponse): Article {
        with(articleResponse) {
            return Article(
                url = url,
                updatedAt = updatedAt,
                section = section,
                title = title,
                abstract = abstract,
                mediaUrl = media[0].mediaMetadata[2].url,//TODO: crashando aqui com index outofbounds.
            )
        }
    }

}