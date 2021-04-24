package com.thomaskioko.stargazer.details.ui.compose.mockdata

import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazers.common.compose.theme.kotlin

internal fun repoViewDataModel() = RepoViewDataModel(
    repoId = 1L,
    repoName = "Material Dialogs",
    description = "\uD83D\uDE0D A beautiful, fluid, and extensible dialogs API for Kotlin & Android.",
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
