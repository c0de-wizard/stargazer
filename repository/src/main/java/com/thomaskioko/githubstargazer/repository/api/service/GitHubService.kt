package com.thomaskioko.githubstargazer.repository.api.service

import com.thomaskioko.githubstargazer.repository.api.model.TopReposResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("search/repositories?q=language:kotlin&order=desc&sort=stars")
    suspend fun getTopRepositories(): TopReposResponse
}
