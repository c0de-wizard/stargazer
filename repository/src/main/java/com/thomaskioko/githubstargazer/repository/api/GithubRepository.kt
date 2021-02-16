package com.thomaskioko.githubstargazer.repository.api

import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity
import com.thomaskioko.githubstargazer.repository.mapper.RepositoryMapper.mapResponseToEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Singleton
class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase
) {

    fun getRepositoryList(isConnected: Boolean): Flow<List<RepoEntity>> =
        database.repoDao().getReposFlow()
            .map { it.firstOrNull() }
            .flatMapConcat { if (it == null && isConnected) loadFromNetwork() else loadCacheRepos() }
            .flowOn(Dispatchers.IO)
            .conflate()

    private suspend fun loadFromNetwork() = flowOf(service.getRepositories())
        .map { database.repoDao().insertRepos(mapResponseToEntityList(it)) }
        .flatMapConcat { loadCacheRepos() }
        .catch { loadCacheRepos().map { emit(it) } }

    private fun loadCacheRepos() = database.repoDao().getReposFlow()

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
