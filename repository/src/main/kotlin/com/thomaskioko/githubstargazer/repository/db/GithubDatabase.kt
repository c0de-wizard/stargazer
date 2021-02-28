package com.thomaskioko.githubstargazer.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomaskioko.githubstargazer.repository.db.dao.RepoDao
import com.thomaskioko.githubstargazer.repository.db.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}
