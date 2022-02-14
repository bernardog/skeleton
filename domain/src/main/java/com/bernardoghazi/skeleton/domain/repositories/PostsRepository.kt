package com.bernardoghazi.skeleton.domain.repositories

import com.bernardoghazi.skeleton.domain.models.Post

interface PostsRepository {

    /**
     * Fetches posts from the backend, and returns them mapped to a list of [Post], or null in case of error.
     * */
    suspend fun fetchPosts(): List<Post>?

    /**
     * Fetches the subscriber's count from the backend. Returns the count, or null in case of error.
     * */
    suspend fun fetchSubscribersCount(url: String?): Int?

}