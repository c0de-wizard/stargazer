package com.thomaskioko.githubstargazer.browse_mvi.model

internal data class RepoViewDataModel(
    val repoId: Long,
    val name: String,
    val description: String?,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val isBookmarked: Boolean
)
