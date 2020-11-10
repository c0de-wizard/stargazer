package com.thomaskioko.githubstargazer.repository.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(movie: RepoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(movies: List<RepoEntity>)

    @Query("SELECT * FROM repo where repoId = :repoId")
    suspend fun getRepoById(repoId: Int): RepoEntity

    @Query("DELETE FROM repo")
    suspend fun clearRepos()
}