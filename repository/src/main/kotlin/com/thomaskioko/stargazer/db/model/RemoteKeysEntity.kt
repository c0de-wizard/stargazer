package com.thomaskioko.stargazer.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey
    var repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)