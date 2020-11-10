package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

object MockEntityData {

    fun makeRepoEntity(repoId: Long) = RepoEntity(
        repoId = repoId,
        name = "Square",
        description = "Some cool description about the app",
        userName = "ninja",
        stargazersCount = 1,
        forksCount = 1,
        contributorsUrl = "",
        createdDate = "1/11/1900",
        updatedDate = "1/11/1900"
    )
}