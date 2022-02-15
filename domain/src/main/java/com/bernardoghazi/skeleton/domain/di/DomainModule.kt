package com.bernardoghazi.skeleton.domain.di

import com.bernardoghazi.skeleton.domain.usecases.FetchMostViewedArticlesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    factory { FetchMostViewedArticlesUseCase(get()) }
}