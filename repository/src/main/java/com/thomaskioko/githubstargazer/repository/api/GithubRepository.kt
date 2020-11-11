package com.thomaskioko.githubstargazer.repository.api

import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity
import com.thomaskioko.githubstargazer.repository.mapper.RepositoryMapper.mapResponseToEntityList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase
) {

    suspend fun getTopRepos(isConnected: Boolean): List<RepoEntity> {
        return if (isConnected) {
            val response = service.getTopRepositories()
            val entityList = mapResponseToEntityList(response)
            database.repoDao().insertRepos(entityList)
            database.repoDao().getRepos()
        } else {
            database.repoDao().getRepos()
        }
    }
}
