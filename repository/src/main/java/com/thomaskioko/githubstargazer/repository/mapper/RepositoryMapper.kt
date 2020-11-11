package com.thomaskioko.githubstargazer.repository.mapper

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

object RepositoryMapper {

    fun mapResponseToEntityList(response: List<RepoResponse>): List<RepoEntity> = response.map {
        RepoEntity(
            repoId = it.id,
            name = it.name,
            description = it.description,
            userName = it.owner.login,
            stargazersCount = it.stargazersCount,
            forksCount = it.forksCount,
            contributorsUrl = it.contributorsUrl,
            createdDate = it.createdDate,
            updatedDate = it.updatedDate
        )
    }
}