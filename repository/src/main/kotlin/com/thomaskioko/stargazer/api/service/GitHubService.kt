package com.thomaskioko.stargazer.api.service

import com.thomaskioko.stargazer.api.model.RepoResponse
import com.thomaskioko.stargazer.api.model.TrendingRepositoriesResponse
import retrofit2.http.GET

interface GitHubService {

    @GET("orgs/square/repos")
    suspend fun getRepositories(): List<RepoResponse>

    @GET("search/repositories?q=android+language:kotlin&created=2021-01-27&sort=stars&order=desc")
    suspend fun getTrendingRepositories(): TrendingRepositoriesResponse
}
