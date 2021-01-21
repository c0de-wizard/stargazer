package com.thomaskioko.githubstargazer.repository.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "repo"
)
data class RepoEntity(
    @PrimaryKey
    val repoId: Long,
    val name: String,
    val description: String?,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val isBookmarked: Boolean = false
)
