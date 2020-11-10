package com.thomaskioko.githubstargazer.repository.api

import com.thomaskioko.githubstargazer.repository.api.model.TopReposResponse
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val gitHubService: GitHubService
) {

    suspend fun getTopRepos(): TopReposResponse = gitHubService.getTopRepositories()
}
