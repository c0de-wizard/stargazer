package com.thomaskioko.githubstargazer.repository.api

import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity
import com.thomaskioko.githubstargazer.repository.mapper.RepositoryMapper.mapResponseToEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@Singleton
class GithubRepository @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase
) {

    fun getRepositoryList(isConnected: Boolean): Flow<List<RepoEntity>> =
        flowOf(isConnected)
            .flatMapConcat { if (it) loadFromNetwork() else database.repoDao().getReposFlow() }
            .flowOn(Dispatchers.IO)
            .conflate()

    private suspend fun loadFromNetwork() = service.getRepositories()
        .map { database.repoDao().insertRepo(mapResponseToEntityList(it)) }
        .asFlow()
        .flatMapConcat { database.repoDao().getReposFlow() }
        .catch { database.repoDao().getReposFlow().map { emit(it) } }

    suspend fun getRepoById(repoId: Long) = database.repoDao().getRepoById(repoId)

    suspend fun getBookmarkedRepos() = database.repoDao().getBookmarkedRepos()

    suspend fun updateRepoBookMarkStatus(isBookMarked: Int, repoId: Long) =
        database.repoDao().setBookmarkStatus(isBookMarked, repoId)
}
