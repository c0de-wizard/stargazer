package com.thomaskioko.githubstargazer.repository.injection

import android.content.Context
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    fun provideGithubDatabase(context: Context) = GithubDatabase.getInstance(context)

    @Provides
    fun provideRepoDao(db: GithubDatabase) = db.repoDao()
}
