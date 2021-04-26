package com.thomaskioko.stargazer.details.model

import androidx.compose.ui.graphics.Color
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazers.common.compose.theme.css
import com.thomaskioko.stargazers.common.compose.theme.java
import com.thomaskioko.stargazers.common.compose.theme.kotlin
import com.thomaskioko.stargazers.common.compose.theme.other
import com.thomaskioko.stargazers.common.compose.theme.swift

internal object ViewDataMapper {

    fun mapEntityToRepoViewModel(entity: RepoEntity) =
        RepoViewDataModel(
            repoId = entity.id,
            repoName = entity.name,
            description = entity.description ?: "",
            userName = entity.userName,
            stargazersCount = entity.stargazersCount,
            forksCount = entity.forksCount,
            contributorsUrl = entity.contributorsUrl,
            createdDate = entity.createdDate,
            updatedDate = entity.updatedDate,
            language = entity.language,
            avatarUrl = entity.avatarUrl,
            languageDrawable = R.drawable.ic_language_drawable,
            drawableColor = mapLanguageToColor(entity.language),
            isBookmarked = entity.isBookmarked
        )

    private fun mapLanguageToColor(language: String): Color {
        return when(language){
            "Kotlin" -> kotlin
            "java" -> java
            "swift" -> swift
            "css" -> css
            else -> other
        }
    }
}
