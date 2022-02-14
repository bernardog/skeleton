package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.UseCaseOutcome
import com.bernardoghazi.skeleton.domain.repositories.PostsRepository

/**
 * Use case responsible for fetching the subscriber's count from the backend (through the [postsRepository]).
 * */
class FetchSubscribersCountUseCase(private val postsRepository: PostsRepository) {
    suspend operator fun invoke(url: String): UseCaseOutcome<Int> {
        postsRepository.fetchSubscribersCount(url)?.let {
            return UseCaseOutcome.Success(it)
        } ?: return UseCaseOutcome.Error()
    }
}