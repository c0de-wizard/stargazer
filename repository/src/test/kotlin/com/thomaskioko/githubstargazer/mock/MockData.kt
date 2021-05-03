package com.thomaskioko.githubstargazer.mock

import androidx.paging.PagingData
import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.api.model.RepositoriesResponse
import com.thomaskioko.stargazer.api.model.UserResponse
import com.thomaskioko.stargazer.db.model.RepoEntity

internal object MockData {

    fun makeRepoResponseList() = listOf(
        RepoResponse(
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            owner = UserResponse(
                id = 1L,
                login = "ninja",
                avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
                type = "Organisation"
            ),
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin"
        )
    )

    fun makeRepositoryResponseList() = RepositoriesResponse(
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
                    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
                    type = "Organisation"
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
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            userType = "Organisation",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            isTrending = false
        )
    )

    fun makeTrendingRepoEntityList() = listOf(
        RepoEntity(
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            userType = "Organisation",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            isTrending = true
        )
    )

    fun makeRepoEntity(repoId: Long, isTrending: Boolean) = RepoEntity(
        id = repoId,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        userType = "Organisation",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isTrending = isTrending,
        language = "Kotlin",
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    )

    fun makeRepoEntity(repoId: Long, name: String) = RepoEntity(
        id = repoId,
        name = name,
        description = "Some cool description about the app",
        userName = "ninja",
        userType = "Organisation",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isTrending = false,
        language = "Kotlin",
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    )

    fun makeRepositoryResponseList(response: List<RepoResponse>) = RepositoriesResponse(
        totalCount = 2222,
        incompleteResults = false,
        repositoriesList = response
    )

    fun makeResponse(id: Long, name: String) = RepoResponse(
        id = id,
        name = name,
        description = "Some cool description about the app",
        owner = UserResponse(
            id = 1L,
            login = "ninja",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            type = "Organisation"
        ),
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        language = "Kotlin"
    )

    fun makePagingSearchEntity() = PagingData.from(
        listOf(
            makeRepoEntity(124, "Suqare"),
            makeRepoEntity(24, "Supra")
        )
    )

    fun makeSearchEntity() = listOf(
        makeRepoEntity(124, "Suqare"),
        makeRepoEntity(24, "Supra")
    )
}
