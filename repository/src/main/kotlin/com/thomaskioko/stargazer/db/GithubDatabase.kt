package com.thomaskioko.stargazer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomaskioko.stargazer.db.dao.RepoDao
import com.thomaskioko.stargazer.db.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
}
