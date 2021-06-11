package com.thomaskioko.stargazers.common.compose.components.mockdata

import androidx.paging.PagingData
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.java
import com.thomaskioko.stargazers.common.compose.theme.kotlin
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.flowOf

/**
 * A fake repo returning sample data
 */
object RepoRepository {
    fun getLazyRepositoryList() = flowOf(PagingData.from(reposList))
    fun getRepositoryList() = reposList
    fun getRepository(): RepoViewDataModel = reposList.random()
}

private val repo1 = RepoViewDataModel(
    repoId = 1,
    repoName = "Turbine",
    description = "Turbine is a small testing library for kotlinx.coroutines Flow.",
    avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
    userName = "cashapp",
    userType = "Organisation",
    stargazersCount = "2k",
    forksCount = "678",
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    language = "Kotlin",
    languageDrawable = R.drawable.ic_language_drawable,
    drawableColor = kotlin,
    isBookmarked = false
)

private val repo2 = RepoViewDataModel(
    repoId = 2,
    repoName = "Architecture Samples",
    description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "android",
    userType = "Organisation",
    stargazersCount = "2k",
    forksCount = "32",
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    language = "Kotlin",
    languageDrawable = R.drawable.ic_language_drawable,
    drawableColor = kotlin,
    isBookmarked = false
)
private val repo3 = RepoViewDataModel(
    repoId = 3,
    repoName = "Shadowsocks-android",
    description = "A shadowsocks client for Android.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    userType = "Organisation",
    stargazersCount = "2k",
    forksCount = "4",
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    language = "Kotlin",
    languageDrawable = R.drawable.ic_language_drawable,
    drawableColor = java,
    isBookmarked = false
)
private val repo4 = RepoViewDataModel(
    repoId = 4,
    repoName = "Leakcanary",
    description = "A memory leak detection library for Android",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    userType = "Organisation",
    stargazersCount = "563",
    forksCount = "221",
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    language = "Java",
    languageDrawable = R.drawable.ic_language_drawable,
    drawableColor = java,
    isBookmarked = false
)
private val repo5 = RepoViewDataModel(
    repoId = 5,
    repoName = "Material-dialogs",
    description = "\uD83D\uDE0D A beautiful, fluid, and extensible dialogs API for Kotlin & Android.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    userType = "Organisation",
    stargazersCount = "23k",
    forksCount = "213",
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    language = "Kotlin",
    languageDrawable = R.drawable.ic_language_drawable,
    drawableColor = kotlin,
    isBookmarked = false
)

private val reposList = listOf(
    repo1,
    repo2,
    repo3,
    repo4,
    repo5,
    repo1.copy(repoId = 6L),
    repo2.copy(repoId = 7L),
    repo3.copy(repoId = 8L),
    repo4.copy(repoId = 9L),
    repo5.copy(repoId = 10L)
)
