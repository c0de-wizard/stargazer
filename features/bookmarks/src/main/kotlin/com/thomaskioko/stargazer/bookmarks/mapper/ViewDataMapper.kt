package com.thomaskioko.stargazer.bookmarks.mapper

import androidx.compose.ui.graphics.Color
import com.thomaskioko.stargazer.bookmarks.R
import com.thomaskioko.stargazer.core.util.countFormatter
import com.thomaskioko.stargazer.db.model.RepoEntity
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import com.thomaskioko.stargazers.common.compose.theme.css
import com.thomaskioko.stargazers.common.compose.theme.go
import com.thomaskioko.stargazers.common.compose.theme.java
import com.thomaskioko.stargazers.common.compose.theme.javaScript
import com.thomaskioko.stargazers.common.compose.theme.kotlin
import com.thomaskioko.stargazers.common.compose.theme.other
import com.thomaskioko.stargazers.common.compose.theme.php
import com.thomaskioko.stargazers.common.compose.theme.ruby
import com.thomaskioko.stargazers.common.compose.theme.swift

object ViewDataMapper {

    fun mapEntityToRepoViewModel(entity: RepoEntity) =
        RepoViewDataModel(
            repoId = entity.id,
            repoName = entity.name,
            description = entity.description ?: "",
            userName = entity.userName,
            stargazersCount = countFormatter(entity.stargazersCount.toLong()) ,
            forksCount = countFormatter(entity.forksCount.toLong()),
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
        return when {
            language.equals("Kotlin", ignoreCase = true) -> kotlin
            language.equals("java", ignoreCase = true) -> java
            language.equals("javascript", ignoreCase = true) -> javaScript
            language.equals("go", ignoreCase = true) -> go
            language.equals("ruby", ignoreCase = true) -> ruby
            language.equals("swift", ignoreCase = true) -> swift
            language.equals("php", ignoreCase = true) -> php
            language.equals("css", ignoreCase = true) -> css
            else -> other
        }
    }
}
