package com.thomaskioko.githubstargazer.repository.api.service

import com.thomaskioko.githubstargazer.repository.api.model.RepoResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("orgs/square/repos")
    suspend fun getTopRepositories(): List<RepoResponse>
}
