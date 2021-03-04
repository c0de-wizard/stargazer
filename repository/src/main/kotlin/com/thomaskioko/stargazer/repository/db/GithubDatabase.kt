package com.thomaskioko.stargazer.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomaskioko.stargazer.repository.db.dao.RepoDao
import com.thomaskioko.stargazer.repository.db.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}
