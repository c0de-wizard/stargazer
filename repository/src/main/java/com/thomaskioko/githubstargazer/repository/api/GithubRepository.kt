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

    suspend fun getRepos(isConnected: Boolean): List<RepoEntity> {
        return if (isConnected) {
            val response = service.getRepositories()
            val entityList = mapResponseToEntityList(response)
            database.repoDao().insertRepos(entityList)
            database.repoDao().getRepos()
        } else {
            database.repoDao().getRepos()
        }
    }

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(repoId: Long, isBookMarked: Boolean) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
