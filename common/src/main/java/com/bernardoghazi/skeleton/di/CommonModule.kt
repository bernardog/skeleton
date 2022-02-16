package com.bernardoghazi.skeleton.di

import com.bernardoghazi.skeleton.common.ImageLoader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val commonModule = module {
    single { ImageLoader(get()) }
}