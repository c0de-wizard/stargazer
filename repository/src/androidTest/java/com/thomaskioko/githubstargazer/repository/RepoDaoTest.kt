package com.thomaskioko.githubstargazer.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.thomaskioko.githubstargazer.repository.MockEntityData.makeRepoEntity
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
internal class RepoDaoTest {

    private lateinit var database: GithubDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, GithubDatabase::class.java
        ).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun givenRepos_verifyDataIsSaved_onInsertRepos() = runBlocking {

        val movies = listOf(
            makeRepoEntity(12314),
            makeRepoEntity(12)
        )

        database.repoDao().insertRepos(movies)

        val repo = database.repoDao().getRepoById(12314)

        assertEquals("Square", repo.name)
    }

    @Test
    fun givenRepos_verifyDataIsDeleted_onClearRepos() = runBlocking {

        val movies = listOf(
            makeRepoEntity(12314),
            makeRepoEntity(12)
        )

        database.repoDao().insertRepos(movies)

        database.repoDao().clearRepos()

        val repo = database.repoDao().getRepoById(12314)

        assertNull(repo)
    }
}