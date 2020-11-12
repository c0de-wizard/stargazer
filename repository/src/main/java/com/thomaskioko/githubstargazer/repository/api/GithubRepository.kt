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
        val repoList = database.repoDao().getRepos()
        return if (isConnected && repoList.isEmpty()) {
            val response = service.getRepositories()
            database.repoDao().insertRepos(mapResponseToEntityList(response))
            database.repoDao().getRepos()
        } else {
            database.repoDao().getRepos()
        }
    }

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
