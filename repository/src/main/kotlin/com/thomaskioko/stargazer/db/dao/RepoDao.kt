package com.thomaskioko.stargazer.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomaskioko.stargazer.db.model.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(list: List<RepoEntity>)

    @Query("SELECT * FROM repo where isTrending = 0 ORDER BY stargazersCount DESC")
    suspend fun getRepositories(): List<RepoEntity>

    @Query("SELECT * FROM repo where isTrending = 1 ORDER BY stargazersCount DESC")
    suspend fun getTrendingRepositories(): List<RepoEntity>

    @Query("SELECT * FROM repo where isTrending = 1 ORDER BY stargazersCount DESC")
    fun getPagedTrendingRepositories(): PagingSource<Int, RepoEntity>

    @Query("SELECT * FROM repo where repoId = :repoId")
    suspend fun getRepoById(repoId: Long): RepoEntity

    @Query("SELECT * FROM repo where isBookmarked = 1")
    suspend fun getBookmarkedRepos(): List<RepoEntity>

    @Query("SELECT * FROM repo WHERE name LIKE  '%' || :query || '%'")
    suspend fun searchRepository(query: String): List<RepoEntity>

    @Query("UPDATE repo SET isBookmarked = :isBookmarked where repoId = :repoId")
    suspend fun setBookmarkStatus(isBookmarked: Int, repoId: Long)

    @Query("DELETE FROM repo")
    suspend fun clearRepos()
}
