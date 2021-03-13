package com.thomaskioko.stargazer.repository

import com.thomaskioko.stargazer.api.service.GitHubService
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import com.thomaskioko.stargazer.db.GithubDatabase
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.mapper.RepositoryMapper.mapResponseToEntityList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase,
    private val flowNetworkObserver: FlowNetworkObserver,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getRepositoryList(): Flow<List<RepoEntity>> =
        flowNetworkObserver.observeInternetConnection()
            .map { isConnected -> if (isConnected) loadFromNetwork() else loadCacheRepos() }
            .flowOn(ioDispatcher)

    fun getTrendingTrendingRepositories(): Flow<List<RepoEntity>> =
        flowNetworkObserver.observeInternetConnection()
            .map { isConnected -> if (isConnected) loadTrendingFromNetwork() else loadTrendingCacheRepos() }
            .flowOn(ioDispatcher)

    private suspend fun loadTrendingFromNetwork(): List<RepoEntity> {
        val apiResult = service.getTrendingRepositories()
        database.repoDao()
            .insertRepos(mapResponseToEntityList(apiResult.repositoriesList, true))
        return loadTrendingCacheRepos()
    }

    private suspend fun loadFromNetwork(): List<RepoEntity> {
        val apiResult = service.getRepositories()
        database.repoDao().insertRepos(mapResponseToEntityList(apiResult, false))
        return loadCacheRepos()
    }

    private suspend fun loadTrendingCacheRepos() = database.repoDao().getTrendingRepositories()

    private suspend fun loadCacheRepos() = database.repoDao().getRepositories()

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
