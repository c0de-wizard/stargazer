package com.thomaskioko.stargazer.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thomaskioko.stargazer.db.model.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeyEntity: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :id")
    suspend fun getRemoteKeysById(id: Long): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}
