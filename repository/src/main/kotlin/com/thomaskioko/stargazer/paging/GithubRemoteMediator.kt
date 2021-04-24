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
import com.thomaskioko.stargazer.mapper.RepositoryMapper
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class GithubRemoteMediator @Inject constructor(
    private val service: GitHubService,
    private val database: GithubDatabase
) : RemoteMediator<Int, RepoEntity>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: NEWS_API_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }
        }

        try {

            val response = service.getTrendingRepositories(page)

            val endOfPaginationReached =
                response.repositoriesList.isEmpty() || response.repositoriesList.size < NEW_API_PAGE_SIZE

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.repoDao().clearRepos()
                }

                val prevKey = if (page == NEWS_API_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = response.repositoriesList.map {
                    RemoteKeysEntity(repoId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.remoteKeysDao().insertAll(keys)
                database.repoDao()
                    .insertRepos(
                        RepositoryMapper.mapResponseToEntityList(
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

    companion object {
        private const val NEWS_API_STARTING_PAGE_INDEX = 1
        private const val NEW_API_PAGE_SIZE = 20
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { member ->
                // Get the remote keys of the last item retrieved
                database.remoteKeysDao().getRemoteKeysById(member.repoId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepoEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { member ->
                // Get the remote keys of the first items retrieved
                database.remoteKeysDao().getRemoteKeysById(member.repoId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, RepoEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.repoId?.let { repoId ->
                database.remoteKeysDao().getRemoteKeysById(repoId)
            }
        }
    }
}
