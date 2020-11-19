package com.thomaskioko.githubstargazer.browse.domain

import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel

object ViewMockData {

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

    fun makeRepoEntityList() = listOf(
        RepoEntity(
            id = 0,
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
        id = 2,
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
}