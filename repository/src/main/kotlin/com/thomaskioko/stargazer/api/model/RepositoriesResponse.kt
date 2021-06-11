package com.thomaskioko.stargazer.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepositoriesResponse(
    @Json(name = "total_count") val totalCount: Int = 0,
    @Json(name = "incomplete_results") val incompleteResults: Boolean = false,
    @Json(name = "items") val repositoriesList: List<RepoResponse> = emptyList(),
)
