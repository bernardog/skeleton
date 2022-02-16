package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.*
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Use case responsible for fetching the articles from the backend (through the [newsRepository]), and returning an ordered list of [Content]
 * made up of headers, posts and dividers.
 * */
class FetchMostPopularArticlesUseCase(
    private val newsRepository: NewsRepository
) : KoinComponent {

    internal val errorMessageResId: Int by inject(named("error_message"))

    suspend operator fun invoke(): UseCaseOutcome<List<Content>> {
        val articles: List<Article>? = newsRepository.fetchMostPopularArticles()

        val articlesSorted = articles?.sortedByDescending { it.updatedAt }

        val content: MutableList<Content> = mutableListOf()

        articlesSorted?.forEachIndexed { index, article ->
            val currentArticle = articlesSorted[index]
            val previousArticle = (index - 1).let { previousPosition ->
                if (previousPosition < 0) null else articlesSorted[previousPosition]
            }

            val isNewDay = if (previousArticle == null) {
                true
            } else {
                removeHoursMinutesSeconds(currentArticle.updatedAt).compareTo(removeHoursMinutesSeconds(previousArticle.updatedAt)) != 0
            }

            if (isNewDay) {
                content.add(createHeader(article))
                content.add(article)
            } else {
                content.add(createDivider())
                content.add(article)
            }
        }

        return if (content.size != 0) UseCaseOutcome.Success(content) else UseCaseOutcome.Error(errorMessageResId)
    }

    internal fun removeHoursMinutesSeconds(dateTime: String): String {
        try {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateTime)
            return DateFormat.getDateInstance(DateFormat.MEDIUM).format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date().toString()
    }

    private fun createDivider() = Divider
    private fun createHeader(article: Article) = Header(date = removeHoursMinutesSeconds(article.updatedAt))
}