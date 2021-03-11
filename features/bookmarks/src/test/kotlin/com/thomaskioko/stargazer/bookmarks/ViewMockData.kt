package com.thomaskioko.stargazer.bookmarks

import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.db.model.RepoEntity

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
            isBookmarked = true
        ),
        RepoViewDataModel(
            repoId = 2L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            isBookmarked = true
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
            isBookmarked = true
        ),
        RepoEntity(
            repoId = 2L,
            name = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            isBookmarked = true
        )
    )
}
