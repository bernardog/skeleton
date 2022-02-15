package com.bernardoghazi.skeleton

import android.app.Application
import com.bernardoghazi.di.viewModelsModule
import com.bernardoghazi.skeleton.data.di.dataModule
import com.bernardoghazi.skeleton.di.commonModule
import com.bernardoghazi.skeleton.domain.di.domainModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    dataModule,
                    viewModelsModule,
                    domainModule,
                    commonModule
                )
            )
        }
    }
}
