package com.thomaskioko.stargazer.browse_mvi.model

import com.thomaskioko.stargazer.repository.db.model.RepoEntity

internal object ViewDataMapper {

    fun mapEntityToRepoViewModel(entity: RepoEntity) =
        RepoViewDataModel(
            repoId = entity.repoId,
            name = entity.name,
            description = entity.description,
            userName = entity.userName,
            stargazersCount = entity.stargazersCount,
            forksCount = entity.forksCount,
            contributorsUrl = entity.contributorsUrl,
            createdDate = entity.createdDate,
            updatedDate = entity.updatedDate,
            isBookmarked = entity.isBookmarked
        )

    fun mapEntityListToRepoViewModel(list: List<RepoEntity>) =
        list.map { entity ->
            RepoViewDataModel(
                repoId = entity.repoId,
                name = entity.name,
                description = entity.description,
                userName = entity.userName,
                stargazersCount = entity.stargazersCount,
                forksCount = entity.forksCount,
                contributorsUrl = entity.contributorsUrl,
                createdDate = entity.createdDate,
                updatedDate = entity.updatedDate,
                isBookmarked = entity.isBookmarked
            )
        }
}