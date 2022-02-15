package com.bernardoghazi.skeleton.data.remote

import com.bernardoghazi.skeleton.data.model.MostViewedArticlesResponse
import retrofit2.http.GET

interface NewsService {
    @GET("svc/mostpopular/v2/viewed/1.json")
    suspend fun fetchMostViewedArticles(): MostViewedArticlesResponse
}