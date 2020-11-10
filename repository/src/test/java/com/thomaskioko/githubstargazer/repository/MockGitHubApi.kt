package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import com.thomaskioko.githubstargazer.repository.api.model.TopReposResponse
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockGitHubApi @Inject constructor() : GitHubService {

    var repos = listOf<RepoResponse>()

    override suspend fun getTopRepositories(): TopReposResponse {
        return TopReposResponse(repos)
    }
}