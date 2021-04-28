package com.thomaskioko.stargazer.details.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

internal data class RepoViewDataModel(
    val repoId: Long,
    val repoName: String,
    val avatarUrl: String,
    val description: String,
    val userName: String,
    val userType: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val language: String,
    @DrawableRes val languageDrawable: Int,
    val drawableColor: Color,
    val isBookmarked: Boolean
)
