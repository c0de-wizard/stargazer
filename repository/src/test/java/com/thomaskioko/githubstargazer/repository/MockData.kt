package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import com.thomaskioko.githubstargazer.repository.api.model.TopReposResponse
import com.thomaskioko.githubstargazer.repository.api.model.UserResponse
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

object MockData {

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

    fun makeTopReposResponse() = TopReposResponse (
        items = listOf(
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
    )

    fun makeRepoEntityList() = listOf(
        RepoEntity(
            id = 0,
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName =  "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900"
        )
    )
}