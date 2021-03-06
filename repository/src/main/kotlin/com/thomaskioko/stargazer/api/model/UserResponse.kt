package com.thomaskioko.stargazer.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "id") val id: Long,
    @Json(name = "login")val login: String,
    @Json(name = "avatar_url")val avatarUrl: String,
    @Json(name = "type")val type: String
)
