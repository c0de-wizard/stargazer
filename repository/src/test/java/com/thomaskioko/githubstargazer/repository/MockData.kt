package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import com.thomaskioko.githubstargazer.repository.api.model.UserResponse

object MockData {

    fun getRepoResponse() = RepoResponse(
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
}