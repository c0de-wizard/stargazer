package com.thomaskioko.stargazer.api.service

import com.thomaskioko.stargazer.api.model.RepoResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("orgs/square/repos")
    suspend fun getRepositories(): List<RepoResponse>
}
