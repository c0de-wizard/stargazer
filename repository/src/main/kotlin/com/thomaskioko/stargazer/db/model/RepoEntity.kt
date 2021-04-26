package com.thomaskioko.stargazer.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class RepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String?,
    val userName: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val contributorsUrl: String,
    val createdDate: String,
    val updatedDate: String,
    val language: String,
    val avatarUrl: String,
    val isBookmarked: Boolean = false,
    val isTrending: Boolean = false
)
