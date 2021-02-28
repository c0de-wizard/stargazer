package com.thomaskioko.githubstargazer.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(entity: RepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(list: List<RepoEntity>)

    @Query("SELECT * FROM repo ORDER BY stargazersCount DESC")
    suspend fun getRepos(): List<RepoEntity>

    @Query("SELECT * FROM repo where repoId = :repoId")
    suspend fun getRepoById(repoId: Long): RepoEntity

    @Query("SELECT * FROM repo where isBookmarked = 1")
    suspend fun getBookmarkedRepos(): List<RepoEntity>

    @Query("UPDATE repo SET isBookmarked = :isBookmarked where repoId = :repoId")
    suspend fun setBookmarkStatus(isBookmarked: Int, repoId: Long)

    @Query("DELETE FROM repo")
    suspend fun clearRepos()

    @Query("SELECT * FROM repo ORDER BY stargazersCount DESC")
    fun getReposFlow(): Flow<List<RepoEntity>>
}
