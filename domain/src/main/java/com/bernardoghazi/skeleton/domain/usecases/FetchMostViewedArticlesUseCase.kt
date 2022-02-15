package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.*
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository

/**
 * TODO: Use case responsible for fetching the posts from the backend (through the [newsRepository]), and returning an ordered list of [Content]
 * made up of headers, posts and dividers.
 * */
class FetchMostViewedArticlesUseCase(
    private val newsRepository: NewsRepository,
    private val errorMessage: String = "erro"
) {
    suspend operator fun invoke(): UseCaseOutcome<List<Content>> {
        val articles: List<Article>? = newsRepository.fetchMostViewedArticles()
        val content: MutableList<Content> = mutableListOf()

        articles?.forEachIndexed { index, post ->
            val currentArticle = articles[index]
            val previousArticle = (index - 1).let { previousPosition ->
                if (previousPosition < 0) null else articles[previousPosition]
            }

            val isNewDay = if (previousArticle == null) {
                true
            } else {
                currentArticle.updatedAt.compareTo(previousArticle.updatedAt) != 0
            }

            if (isNewDay) {
                content.add(createHeader(post))
                content.add(post)
            } else {
                content.add(createDivider())
                content.add(post)
            }
        }

        return if (content.size != 0) UseCaseOutcome.Success(content) else UseCaseOutcome.Error(errorMessage)
    }

    private fun createDivider() = Divider
    private fun createHeader(article: Article) = Header(date = article.updatedAt)
}