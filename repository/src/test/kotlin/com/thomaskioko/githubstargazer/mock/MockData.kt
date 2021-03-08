package com.thomaskioko.githubstargazer.mock

import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.api.model.UserResponse
import com.thomaskioko.stargazer.db.model.RepoEntity

internal object MockData {

    fun makeRepoResponse() = RepoResponse(
        id = 1L,
        name = "Square",
        description = "Some cool description about the app",
        owner = UserResponse(id = 1L, login = "ninja"),
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900"
    )

    fun makeRepoResponseList() = listOf(
        RepoResponse(
            id = 1L,
            name = "Square",
            description = "Some cool description about the app",
            owner = UserResponse(id = 1L, login = "ninja"),
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900"
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
            updatedDate = "1/11/1900"
        )
    )

    fun makeRepoEntity(repoId: Long) = RepoEntity(
        repoId = repoId,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900"
    )
}
