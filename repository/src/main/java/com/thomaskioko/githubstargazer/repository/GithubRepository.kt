package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.model.TopReposResponse
import com.thomaskioko.githubstargazer.repository.service.GitHubService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val gitHubService: GitHubService
) {

    suspend fun getTopRepos(): TopReposResponse = gitHubService.getTopRepositories()
}
