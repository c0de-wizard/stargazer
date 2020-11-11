package com.thomaskioko.githubstargazer.repository.util

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockGitHubApi @Inject constructor() : GitHubService {

    var repos = listOf<RepoResponse>()

    override suspend fun getRepositories(): List<RepoResponse> {
        return repos
    }
}