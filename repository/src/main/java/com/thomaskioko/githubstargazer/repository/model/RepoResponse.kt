package com.thomaskioko.githubstargazer.repository.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "owner") val owner: UserResponse,
    @Json(name = "stargazers_count") val stargazersCount: Int,
    @Json(name = "forks") val forksCount: Int,
    @Json(name = "contributors_url") val contributorsUrl: String,
    @Json(name = "created_at") val createdDate: String,
    @Json(name = "updated_at") val updatedDate: String
)