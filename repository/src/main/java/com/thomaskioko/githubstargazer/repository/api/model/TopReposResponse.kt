package com.thomaskioko.githubstargazer.repository.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopReposResponse(
    val items: List<RepoResponse>
)