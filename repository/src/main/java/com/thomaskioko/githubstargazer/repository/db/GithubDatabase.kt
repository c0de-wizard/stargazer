package com.thomaskioko.githubstargazer.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {

        @Volatile
        private var instance: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            GithubDatabase::class.java, "github_database.db"
        ).build()
    }
}