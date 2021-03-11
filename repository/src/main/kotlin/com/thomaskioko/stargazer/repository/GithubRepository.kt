package com.thomaskioko.stargazer.repository

import com.thomaskioko.stargazer.api.service.GitHubService
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.db.GithubDatabase
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.mapper.RepositoryMapper.mapResponseToEntityList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getRepositoryList(isConnected: Boolean): Flow<List<RepoEntity>> = flow {
        when {
            isConnected -> emit(loadFromNetwork())
            else -> emit(loadCacheRepos())
        }
    }
        .flowOn(ioDispatcher)

    fun getTrendingTrendingRepositories(isConnected: Boolean): Flow<List<RepoEntity>> = flow {

        val result = when {
            isConnected -> {
                val apiResult = service.getTrendingRepositories()

                database.repoDao().insertRepos(
                    mapResponseToEntityList(apiResult.repositoriesList, true)
                )
                database.repoDao().getTrendingRepositories()
            }
            else -> database.repoDao().getTrendingRepositories()
        }

        emit(result)
    }
        .flowOn(ioDispatcher)

    private suspend fun loadFromNetwork(): List<RepoEntity> {
        val apiResult = service.getRepositories()
        database.repoDao().insertRepos(mapResponseToEntityList(apiResult, false))
        return loadCacheRepos()
    }

    private suspend fun loadCacheRepos() = database.repoDao().getRepositories()

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
