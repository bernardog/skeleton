package com.bernardoghazi.skeleton.domain.di

import com.bernardoghazi.skeleton.domain.usecases.FetchMostPopularArticlesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    factory { FetchMostPopularArticlesUseCase(get()) }
}