package com.thomaskioko.stargazer.repo_details.util

import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.repository.db.model.RepoEntity

internal object ViewMockData {

    fun makeRepoViewDataModelList() = listOf(
        RepoViewDataModel(
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            isBookmarked = false
        )
    )

    fun makeRepoEntity() = RepoEntity(
        repoId = 1L,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isBookmarked = false
    )

    fun makeRepoViewDataModel() = RepoViewDataModel(
        repoId = 1L,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isBookmarked = false
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
}
