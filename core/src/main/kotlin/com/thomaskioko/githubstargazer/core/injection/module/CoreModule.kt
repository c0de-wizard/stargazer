package com.thomaskioko.githubstargazer.core.injection.module

import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideGithubRepository(service: GitHubService, database: GithubDatabase) =
        GithubRepository(service, database)
}
