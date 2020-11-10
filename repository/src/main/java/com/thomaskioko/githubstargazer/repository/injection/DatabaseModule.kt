package com.thomaskioko.githubstargazer.repository.injection

import android.app.Application
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import dagger.Module
import dagger.Provides

@Module
object DatabaseModule {

    @Provides
    fun provideGithubDatabase(app: Application) = GithubDatabase.getInstance(app)

    @Provides
    fun provideRepoDao(db: GithubDatabase) = db.repoDao()

}