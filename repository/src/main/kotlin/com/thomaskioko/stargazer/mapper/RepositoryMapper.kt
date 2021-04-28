package com.thomaskioko.stargazer.mapper

import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.db.model.RepoEntity

object RepositoryMapper {

    fun mapResponseToEntityList(
        response: List<RepoResponse>,
        isTrending: Boolean = false
    ): List<RepoEntity> = response.map {
        RepoEntity(
            id = it.id,
            name = it.name.capitalize(),
            description = it.description,
            userName = it.owner.login,
            userType = it.owner.type,
            stargazersCount = it.stargazersCount,
            forksCount = it.forksCount,
            contributorsUrl = it.contributorsUrl,
            createdDate = it.createdDate,
            updatedDate = it.updatedDate,
            language = it.language ?: "Other",
            avatarUrl = it.owner.avatarUrl,
            isTrending = isTrending
        )
    }
}
