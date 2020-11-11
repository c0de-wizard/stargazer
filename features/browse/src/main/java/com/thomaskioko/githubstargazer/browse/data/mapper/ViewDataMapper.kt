package com.thomaskioko.githubstargazer.browse.data.mapper

import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

object ViewDataMapper {

    fun mapEntityToRepoViewModel(entity: RepoEntity) = RepoViewDataModel(
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