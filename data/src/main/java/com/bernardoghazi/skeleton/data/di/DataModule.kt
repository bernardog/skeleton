package com.bernardoghazi.skeleton.data.di

import com.bernardoghazi.skeleton.data.BuildConfig
import com.bernardoghazi.skeleton.data.remote.NewsService
import com.bernardoghazi.skeleton.data.repository.NewsRepositoryImpl
import com.bernardoghazi.skeleton.domain.repositories.NewsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val TIMEOUT = 30L
const val BASE_URL = "https://api.nytimes.com"

private fun createGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setLenient()
    return gsonBuilder.create()
}

private fun configRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit
    .Builder()
    .client(client)
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

val dataModule = module {

    single { createGson() }

    single {
        HttpLoggingInterceptor()
            .setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
    }

    single {
        Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder().addQueryParameter("api-key", BuildConfig.MOST_VIEWED_API_KEY).build()
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }

    single {
        OkHttpClient.Builder().apply {
            addInterceptor(get<HttpLoggingInterceptor>())
            addInterceptor(get<Interceptor>())
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    single {
        configRetrofit(
            client = get(),
            gson = get()
        )
    }

    factory { get<Retrofit>().create(NewsService::class.java) }

    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}