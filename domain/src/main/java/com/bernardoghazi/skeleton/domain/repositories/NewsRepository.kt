package com.bernardoghazi.skeleton.domain.repositories

import com.bernardoghazi.skeleton.domain.models.Article

interface NewsRepository {

    /**
     * Fetches posts from the backend, and returns them mapped to a list of [Article], or null in case of error.
     * */
    suspend fun fetchMostViewedArticles(): List<Article>?
}