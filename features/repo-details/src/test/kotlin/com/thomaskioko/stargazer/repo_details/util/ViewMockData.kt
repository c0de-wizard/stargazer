package com.thomaskioko.stargazer.repo_details.util

import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazers.common.compose.theme.kotlin

internal object ViewMockData {

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
        language = "Kotlin",
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
        isBookmarked = false
    )

    fun makeRepoViewDataModel() = RepoViewDataModel(
        repoId = 1L,
        repoName = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900",
        isBookmarked = false,
        avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
        language = "Kotlin",
        drawableColor = kotlin,
        languageDrawable = R.drawable.ic_language_drawable,
    )
}
