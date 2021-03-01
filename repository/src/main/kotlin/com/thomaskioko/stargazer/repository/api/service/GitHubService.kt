package com.thomaskioko.stargazer.repository.api.service

import com.thomaskioko.stargazer.repository.api.model.RepoResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("orgs/square/repos")
    suspend fun getRepositories(): List<RepoResponse>
}
