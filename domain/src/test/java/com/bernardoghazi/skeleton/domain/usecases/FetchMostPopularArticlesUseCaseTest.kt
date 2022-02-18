package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.Article
import com.bernardoghazi.skeleton.domain.models.Divider
import com.bernardoghazi.skeleton.domain.models.Header
import com.bernardoghazi.skeleton.domain.models.UseCaseOutcome
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

@ExperimentalCoroutinesApi
class FetchMostPopularArticlesUseCaseTest : KoinComponent {

    private val fakeDomainModule = module {
        factory { FetchMostPopularArticlesUseCase(get()) }
        single(named("error_message")) { 123 }
    }

    @Before
    fun setupClass() {
        startKoin {
            modules(listOf(fakeDomainModule))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        unmockkAll()
    }

    @Test
    fun `when repo returns articles, usecase creates content correctly`() {
        runBlockingTest {
            val newsRepositoryMockk = mockk<NewsRepository>()
            val mostPopularArticles = listOf(
                Article(
                    "url",
                    "2022-02-14 00:10:00",
                    "section",
                    "title",
                    "abstract",
                    "mediaUrl"
                ),

                Article(
                    "url",
                    "2022-02-14 00:11:00",
                    "section",
                    "title",
                    "abstract",
                    "mediaUrl"
                ),

                Article(
                    "url",
                    "2022-02-12 00:10:00",
                    "section",
                    "title",
                    "abstract",
                    "mediaUrl"
                ),

                Article(
                    "url",
                    "2022-02-09 22:22:22",
                    "section",
                    "title",
                    "abstract",
                    "mediaUrl"
                )
            )
            coEvery { newsRepositoryMockk.fetchMostPopularArticles() } returns mostPopularArticles
            mockkStatic(Locale::class)
            every { Locale.getDefault() } returns Locale.UK

            val fetchMostPopularArticlesUseCase = FetchMostPopularArticlesUseCase(newsRepositoryMockk)
            val outcome = fetchMostPopularArticlesUseCase()

            assert(outcome is UseCaseOutcome.Success)
            assert(((outcome as UseCaseOutcome.Success).data[0] as Header).date == "Feb 14, 2022")
            assert(((outcome as UseCaseOutcome.Success).data[1] as Article).updatedAt == "2022-02-14 00:11:00")
            assert(((outcome as UseCaseOutcome.Success).data[2] is Divider))
            assert(((outcome as UseCaseOutcome.Success).data[3] as Article).updatedAt == "2022-02-14 00:10:00")
            assert(((outcome as UseCaseOutcome.Success).data[4] as Header).date == "Feb 12, 2022")
            assert(((outcome as UseCaseOutcome.Success).data[5] as Article).updatedAt == "2022-02-12 00:10:00")
            assert(((outcome as UseCaseOutcome.Success).data[6] as Header).date == "Feb 9, 2022")
            assert(((outcome as UseCaseOutcome.Success).data[7] as Article).updatedAt == "2022-02-09 22:22:22")
        }
    }

    @Test
    fun `when repo returns empty list, usecase returns error`() {
        runBlockingTest {
            val newsRepositoryMockk = mockk<NewsRepository>()
            coEvery { newsRepositoryMockk.fetchMostPopularArticles() } returns listOf()

            val fetchMostPopularArticlesUseCase = FetchMostPopularArticlesUseCase(newsRepositoryMockk)
            val outcome = fetchMostPopularArticlesUseCase()

            assert(outcome is UseCaseOutcome.Error)
            assert((outcome as UseCaseOutcome.Error).errorStringResId == 123)
        }
    }


    @Test
    fun `when date is invalid, removeHoursMinutesSeconds returns current date`() {
        val currentDate = "01/01/2001"
        mockkConstructor(Date::class)
        every { anyConstructed<Date>().toString() } returns currentDate

        val fetchMostPopularArticlesUseCase = FetchMostPopularArticlesUseCase(mockk())

        assert(fetchMostPopularArticlesUseCase.removeHoursMinutesSeconds("2020x-08-28") == currentDate)
    }

    @Test
    fun `when date is valid, removeHoursMinutesSeconds returns date`() {
        mockkStatic(Locale::class)
        every { Locale.getDefault() } returns Locale.UK

        val fetchMostPopularArticlesUseCase = FetchMostPopularArticlesUseCase(mockk())

        assert(fetchMostPopularArticlesUseCase.removeHoursMinutesSeconds("2020-08-28 00:08:50") == "Aug 28, 2020")
    }
}