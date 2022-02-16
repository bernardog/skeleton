package com.bernardoghazi.skeleton.domain.models

/**
 * Sealed class that represents the possible outcomes of our use cases, and wraps the appropriate data in case of success, or an error message in
 * case of error.
 */
sealed class UseCaseOutcome<T> {
    data class Success<T>(val data: T) : UseCaseOutcome<T>()
    data class Error<T>(val errorStringResId: Int? = null) : UseCaseOutcome<T>()
}