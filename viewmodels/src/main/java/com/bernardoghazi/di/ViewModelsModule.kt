package com.bernardoghazi.di

import com.bernardoghazi.viewmodels.ArticlesFragmentViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { ArticlesFragmentViewModel(get()) }
}