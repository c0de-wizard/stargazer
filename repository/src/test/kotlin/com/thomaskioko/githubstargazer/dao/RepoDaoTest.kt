package com.thomaskioko.githubstargazer.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.thomaskioko.githubstargazer.mock.MockData.makeRepoEntity
import com.thomaskioko.stargazer.db.GithubDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class RepoDaoTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var database: GithubDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room
            .inMemoryDatabaseBuilder(context, GithubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun givenRepos_verifyDataIsSaved_onInsertRepos() = runBlocking {

        val list = listOf(
            makeRepoEntity(12314, false),
            makeRepoEntity(12, false)
        )

        database.repoDao().insertRepos(list)
        val repo = database.repoDao().getRepoById(12314)

        assertEquals("Square", repo.name)
    }

    @Test
    fun givenRepos_verifyDataIsReturned_onGetRepos() = runBlocking {

        val list = listOf(
            makeRepoEntity(12314, false),
            makeRepoEntity(12, true),
            makeRepoEntity(14, false),
            makeRepoEntity(17, true)
        )

        database.repoDao().insertRepos(list)
        val query = database.repoDao().getRepositories()
        assertEquals(query.size, 2)
    }

    @Test
    fun givenRepos_verifyDataIsReturned_onTrendingRepositories() = runBlocking {

        val list = listOf(
            makeRepoEntity(12314, false),
            makeRepoEntity(12, true),
            makeRepoEntity(14, false),
            makeRepoEntity(19, true)
        )

        database.repoDao().insertRepos(list)
        val query = database.repoDao().getTrendingRepositories()
        assertEquals(query.size, 2)
    }

    @Test
    fun givenRepos_verifyDataIsDeleted_onClearRepos() = runBlocking {

        val list = listOf(
            makeRepoEntity(12314, false),
            makeRepoEntity(12, false)
        )

        database.repoDao().insertRepos(list)

        database.repoDao().clearRepos()

        val repo = database.repoDao().getRepoById(12314)

        assertNull(repo)
    }

    @Test
    fun givenIsUpdated_verifyLatestDataIsReturned_onGetBookmarkedRepos() = runBlocking {

        val list = listOf(
            makeRepoEntity(12314, false),
            makeRepoEntity(12, false)
        )

        database.repoDao().insertRepos(list)

        val repo = database.repoDao().getRepoById(12314)
        assertEquals(repo.isBookmarked, false)

        // Update repo bookMarkState
        database.repoDao().setBookmarkStatus(1, 12314)

        val bookMarkList = database.repoDao().getBookmarkedRepos()
        assertEquals(bookMarkList.size, 1)

        val updatedRepo = database.repoDao().getRepoById(12314)

        assertEquals(updatedRepo.isBookmarked, true)
    }
}
