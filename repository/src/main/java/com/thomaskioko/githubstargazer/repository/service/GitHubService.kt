package com.thomaskioko.githubstargazer.repository.service

import com.thomaskioko.githubstargazer.repository.model.TopReposResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("search/repositories?q=language:kotlin&order=desc&sort=stars")
    suspend fun getTopRepositories(): TopReposResponse
}
