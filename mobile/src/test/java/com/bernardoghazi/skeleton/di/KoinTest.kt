//package com.bernardoghazi.skeleton.di
//
//import com.bernardoghazi.di.viewModelsModule
//import com.bernardoghazi.skeleton.data.di.dataModule
//import com.bernardoghazi.skeleton.domain.di.domainModule
//import org.junit.Test
//import org.koin.core.component.KoinComponent
//import org.koin.dsl.koinApplication
//
//class KoinTest : KoinComponent {
//
//    @Test
//    fun `checking modules`() {
//        koinApplication {
//            modules(
//                listOf(
//                    dataModule,
//                    viewModelsModule,
//                    domainModule,
//                    commonModule
//                )
//            )
//        }.checkModules()
//    }
//}