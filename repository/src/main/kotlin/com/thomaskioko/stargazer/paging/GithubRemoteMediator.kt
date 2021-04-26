package com.thomaskioko.stargazer.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.thomaskioko.stargazer.api.service.GitHubService
import com.thomaskioko.stargazer.db.GithubDatabase
import com.thomaskioko.stargazer.db.model.RemoteKeysEntity
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.mapper.RepositoryMapper.mapResponseToEntityList
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase
) : RemoteMediator<Int, RepoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> return pageKeyData
            else -> pageKeyData as Int
        }

        try {
            val response = service.getTrendingRepositories(page= page)
            val endOfPaginationReached = response.repositoriesList.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                   database.remoteKeysDao().clearRemoteKeys()
                   database.repoDao().clearRepos()
                }

                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.repositoriesList.map {
                    RemoteKeysEntity(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.repoDao().insertRepos(
                        mapResponseToEntityList(
                            response = response.repositoriesList,
                            isTrending = true
                        )
                    )
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeysDao().getRemoteKeysById(repoId)
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { repoEntity -> database.remoteKeysDao().getRemoteKeysById(repoEntity.id) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { repoEntity -> database.remoteKeysDao().getRemoteKeysById(repoEntity.id) }
    }
}
