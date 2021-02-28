package com.thomaskioko.githubstargazer.repository.injection

import android.content.Context
import androidx.room.Room
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideGithubDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app, GithubDatabase::class.java,
        "github_database"
    ).build()

    @Provides
    fun provideRepoDao(db: GithubDatabase) = db.repoDao()
}
