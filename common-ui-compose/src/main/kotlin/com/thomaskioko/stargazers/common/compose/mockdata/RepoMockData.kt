package com.thomaskioko.stargazers.common.compose.mockdata

import androidx.compose.runtime.Immutable


@Immutable
data class Repo(
    val repoId: Long,
    val name: String,
    val avatarUrl: String,
    val description: String,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val isBookmarked: Boolean
)

/**
 * A fake repo returning sample data
 */
object RepoRepository {
    fun getRepositoryList(): List<Repo> = reposList
    fun getRepository(): Repo = reposList.random()
}

private val repo1 = Repo(
    repoId = 1,
    name = "Turbine",
    description = "Turbine is a small testing library for kotlinx.coroutines Flow.",
    avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
    userName = "cashapp",
    stargazersCount = 2788,
    forksCount = 678,
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    isBookmarked = false
)

private val repo2 = Repo(
    repoId = 2,
    name = "Architecture Samples",
    description = "A collection of samples to discuss and showcase different architectural tools and patterns for Android apps.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "android",
    stargazersCount = 2345,
    forksCount = 32,
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    isBookmarked = false
)
private val repo3 = Repo(
    repoId = 3,
    name = "Shadowsocks-android",
    description = "A shadowsocks client for Android.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    stargazersCount = 2788,
    forksCount = 4,
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    isBookmarked = false
)
private val repo4 = Repo(
    repoId = 4,
    name = "Leakcanary",
    description = "A memory leak detection library for Android",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    stargazersCount = 563,
    forksCount = 221,
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
    isBookmarked = false
)
private val repo5 = Repo(
    repoId = 5,
    name = "Material-dialogs",
    description = "\uD83D\uDE0D A beautiful, fluid, and extensible dialogs API for Kotlin & Android.",
    avatarUrl = "https://avatars.githubusercontent.com/u/32689599?v=4",
    userName = "cashapp",
    stargazersCount = 23488,
    forksCount = 213,
    contributorsUrl = "https://github.com/cashapp/turbine",
    createdDate = "12/01/2003",
    updatedDate = "11/03/2021",
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