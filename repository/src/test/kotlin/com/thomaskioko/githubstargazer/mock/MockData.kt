package com.thomaskioko.githubstargazer.mock

import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.api.model.TrendingRepositoriesResponse
import com.thomaskioko.stargazer.api.model.UserResponse
import com.thomaskioko.stargazer.db.model.RepoEntity

internal object MockData {

    fun makeRepoResponse() = RepoResponse(
        id = 1L,
        name = "Square",
        description = "Some cool description about the app",
        owner = UserResponse(id = 1L, login = "ninja", avatarUrl = "", type = "Organization"),
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        language = "Kotlin"
    )

    fun makeRepoResponseList() = listOf(
        RepoResponse(
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            owner = UserResponse(id = 1L, login = "ninja", avatarUrl = "", type = "Organization"),
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin"
        )
    )

    fun makeTrendingRepoResponseList() = TrendingRepositoriesResponse(
        totalCount = 2222,
        incompleteResults = false,
        repositoriesList = listOf(
            RepoResponse(
                id = 1L,
                name = "Square",
                description = "Some cool description about the app",
                owner = UserResponse(
                    id = 1L,
                    login = "ninja",
                    avatarUrl = "",
                    type = "Organization"
                ),
                stargazersCount = 1,
                forksCount = 1,
                contributorsUrl = "",
                createdDate = "1/11/1900",
                updatedDate = "1/11/1900",
                language = "Kotlin"
            )
        )
    )

    fun makeRepoEntityList() = listOf(
        RepoEntity(
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            isTrending = false
        )
    )

    fun makeTrendingRepoEntityList() = listOf(
        RepoEntity(
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            isTrending = true
        )
    )

    fun makeRepoEntity(repoId: Long, isTrending: Boolean) = RepoEntity(
        repoId = repoId,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isTrending = isTrending
    )
}
