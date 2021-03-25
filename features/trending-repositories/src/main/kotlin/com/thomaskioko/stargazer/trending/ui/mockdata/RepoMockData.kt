package com.thomaskioko.stargazer.trending.ui.mockdata

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.thomaskioko.stargazer.trending.model.RepoViewDataModel
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.java
import com.thomaskioko.stargazers.common.compose.theme.kotlin


@Immutable
data class RepoViewDataModel(
    val repoId: Long,
    val repoName: String,
    val avatarUrl: String,
    val description: String,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val language: String,
    @DrawableRes val languageDrawable: Int,
    val drawableColor: Color,
    val isBookmarked: Boolean
)

/**
 * A fake repo returning sample data
 */
object RepoRepository {
    fun getRepositoryList(): List<RepoViewDataModel> = reposList
    fun getRepository(): RepoViewDataModel = reposList.random()
}

private val repo1 = RepoViewDataModel(
    repoId = 1,
    repoName = "Turbine",
    description = "Turbine is a small testing library for kotlinx.coroutines Flow.",
    avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
    userName = "cashapp",
    stargazersCount = 2788,
    forksCount = 678,
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
    stargazersCount = 2345,
    forksCount = 32,
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
    stargazersCount = 2788,
    forksCount = 4,
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
    stargazersCount = 563,
    forksCount = 221,
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
    stargazersCount = 23488,
    forksCount = 213,
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
