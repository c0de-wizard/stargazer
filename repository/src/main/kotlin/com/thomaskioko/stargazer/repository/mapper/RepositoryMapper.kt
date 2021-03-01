package com.thomaskioko.stargazer.repository.mapper

import com.thomaskioko.stargazer.repository.api.model.RepoResponse
import com.thomaskioko.stargazer.repository.db.model.RepoEntity

object RepositoryMapper {

    fun mapResponseToEntityList(response: List<RepoResponse>): List<RepoEntity> = response.map {
        mapResponseToEntityList(it)
    }

    fun mapResponseToEntityList(response: RepoResponse): RepoEntity =
        RepoEntity(
            repoId = response.id,
            name = response.name.capitalize(),
            description = response.description,
            userName = response.owner.login,
            stargazersCount = response.stargazersCount,
            forksCount = response.forksCount,
            contributorsUrl = response.contributorsUrl,
            createdDate = response.createdDate,
            updatedDate = response.updatedDate
        )
}
