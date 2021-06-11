package com.thomaskioko.stargazer.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.thomaskioko.stargazer.api.service.GitHubService
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import com.thomaskioko.stargazer.db.GithubDatabase
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.mapper.RepositoryMapper.mapResponseToEntityList
import com.thomaskioko.stargazer.paging.GithubRemoteMediator
import com.thomaskioko.stargazer.paging.GithubSearchRemoteMediator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase,
    private val remoteMediator: GithubRemoteMediator,
    private val flowNetworkObserver: FlowNetworkObserver,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    fun getRepositoryList(): Flow<List<RepoEntity>> =
        flowNetworkObserver.observeInternetConnection()
            .map { isConnected -> if (isConnected) loadFromNetwork() else loadCacheRepos() }
            .flowOn(ioDispatcher)

    @OptIn(ExperimentalPagingApi::class)
    fun getTrendingTrendingRepositories(): Flow<PagingData<RepoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.repoDao().getPagedTrendingRepositories() }
        ).flow
    }

    @OptIn(ExperimentalPagingApi::class)
    fun searchRepository(query: String): Flow<PagingData<RepoEntity>> {
        return Pager(
            config =  PagingConfig(
                pageSize = 10,
                initialLoadSize = 30,
                enablePlaceholders = false,
                prefetchDistance = 1,
            ),
            remoteMediator = GithubSearchRemoteMediator(query, service, database),
            pagingSourceFactory = { database.repoDao().searchRepoByName(query) }
        ).flow
    }

    private suspend fun loadFromNetwork(): List<RepoEntity> {
        val apiResult = service.getRepositories()
        database.repoDao().insertRepos(mapResponseToEntityList(apiResult))
        return loadCacheRepos()
    }

    private suspend fun loadCacheRepos() = database.repoDao().getRepositories()

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
