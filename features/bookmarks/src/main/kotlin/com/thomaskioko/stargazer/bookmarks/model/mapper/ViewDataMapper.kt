package com.thomaskioko.stargazer.bookmarks.model.mapper

import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.db.model.RepoEntity

object ViewDataMapper {

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
}
