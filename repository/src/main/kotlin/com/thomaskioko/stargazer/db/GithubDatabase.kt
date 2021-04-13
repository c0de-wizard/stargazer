package com.thomaskioko.stargazer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thomaskioko.stargazer.db.dao.RemoteKeysDao
import com.thomaskioko.stargazer.db.dao.RepoDao
import com.thomaskioko.stargazer.db.model.RemoteKeysEntity
import com.thomaskioko.stargazer.db.model.RepoEntity

@Database(
    entities = [
        RepoEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
