package com.thomaskioko.githubstargazer.browse.data.model

data class RepoViewDataModel(
    val repoId: Long,
    val name: String,
    val description: String?,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String
)