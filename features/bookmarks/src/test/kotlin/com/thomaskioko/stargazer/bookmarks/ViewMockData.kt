package com.thomaskioko.stargazer.bookmarks

import androidx.compose.ui.graphics.Color
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazers.common.model.RepoViewDataModel

object ViewMockData {

    fun makeRepoViewDataModelList() = listOf(
        RepoViewDataModel(
            repoId = 1L,
            repoName = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            languageDrawable = R.drawable.ic_language_drawable,
            drawableColor = Color(0xFFF08E33),
            isBookmarked = true
        ),
        RepoViewDataModel(
            repoId = 2L,
            repoName = "Square",
            description = "Some cool description about the app",
            userName = "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900",
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            languageDrawable = R.drawable.ic_language_drawable,
            drawableColor = Color(0xFFF08E33),
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
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
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
            language = "Kotlin",
            avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
            isBookmarked = true
        )
    )
}
