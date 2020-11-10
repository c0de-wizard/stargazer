package com.thomaskioko.githubstargazer.repository.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopReposResponse(
    val items: List<RepoResponse>
)