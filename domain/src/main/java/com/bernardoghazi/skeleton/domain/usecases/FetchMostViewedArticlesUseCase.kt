package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.*
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository


fun main() {
    val list = listOf(
        "2022-02-15 09:00:00",
        "2022-02-15 10:00:00",
        "2022-02-15 11:00:00",
        "2022-02-14 09:00:00",
        "2022-02-13 10:00:00",
        "2022-02-13 09:00:00",
    )

    val sortedBy = list.sortedBy { it }

    sortedBy.forEach { println(it) }
}


/**
 * TODO: Use case responsible for fetching the articles from the backend (through the [newsRepository]), and returning an ordered list of [Content]
 * made up of headers, posts and dividers.
 * */
class FetchMostPopularArticlesUseCase(
    private val newsRepository: NewsRepository,
    private val errorMessage: String = "erro"//TODO
) {
    suspend operator fun invoke(): UseCaseOutcome<List<Content>> {
        val articles: List<Article>? = newsRepository.fetchMostPopularArticles()?.sortedByDescending { it.updatedAt }
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