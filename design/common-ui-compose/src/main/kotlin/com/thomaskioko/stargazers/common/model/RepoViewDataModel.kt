package com.thomaskioko.stargazers.common.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class RepoViewDataModel(
    val repoId: Long,
    val repoName: String,
    val avatarUrl: String,
    val description: String,
    val userName: String,
    val stargazersCount: String,
    val forksCount: String,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val language: String,
    @DrawableRes val languageDrawable: Int,
    val drawableColor: Color,
    val isBookmarked: Boolean
)
