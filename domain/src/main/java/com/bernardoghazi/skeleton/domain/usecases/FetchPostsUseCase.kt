package com.bernardoghazi.skeleton.domain.usecases

import com.bernardoghazi.skeleton.domain.models.*
import com.bernardoghazi.skeleton.domain.repositories.PostsRepository

/**
 * Use case responsible for fetching the posts from the backend (through the [postsRepository]), and returning an ordered list of [Content] made up of headers, posts and dividers.
 * */
class FetchPostsUseCase(
    private val postsRepository: PostsRepository,
    private val errorMessage: String
) {
    suspend operator fun invoke(): UseCaseOutcome<List<Content>> {
        val posts: List<Post>? = postsRepository.fetchPosts()
        val content: MutableList<Content> = mutableListOf()

        posts?.forEachIndexed { index, post ->
            val currentPost = posts[index]
            val previousPost = (index - 1).let { previousPosition ->
                if (previousPosition < 0) null else posts[previousPosition]
            }

            val isNewDay = if (previousPost == null) {
                true
            } else {
                currentPost.date.compareTo(previousPost.date) != 0
            }

            if (isNewDay) {
                content.add(createHeader(post))
                content.add(post)
            } else {
                content.add(createDivider())
                content.add(post)
            }
        }

        return if (content.size != 0) UseCaseOutcome.Success(content) else UseCaseOutcome.Error(errorMessage)
    }

    private fun createDivider() = Divider
    private fun createHeader(post: Post) = Header(date = post.date)
}