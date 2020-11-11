package com.thomaskioko.githubstargazer.browse.data

import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

object ViewMockData {

    fun makeRepoViewDataModelList() = listOf(
        RepoViewDataModel(
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName =  "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900"
        )
    )

    fun makeRepoEntityList() = listOf(
        RepoEntity(
            id = 0,
            repoId = 1L,
            name = "Square",
            description = "Some cool description about the app",
            userName =  "ninja",
            stargazersCount = 1,
            forksCount = 1,
            contributorsUrl = "",
            createdDate = "1/11/1900",
            updatedDate = "1/11/1900"
        )
    )
}