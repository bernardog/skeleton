package com.bernardoghazi.skeleton.domain.di

import com.bernardoghazi.skeleton.common.R
import com.bernardoghazi.skeleton.domain.usecases.FetchMostPopularArticlesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val domainModule = module {
    factory { FetchMostPopularArticlesUseCase(get()) }
    single(named("error_message")) { R.string.error_message }
}