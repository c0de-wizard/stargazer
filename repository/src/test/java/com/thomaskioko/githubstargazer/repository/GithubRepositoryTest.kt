package com.thomaskioko.githubstargazer.repository

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.repository.MockData.getRepoEntityList
import com.thomaskioko.githubstargazer.repository.MockData.getRepoResponse
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.dao.RepoDao
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GithubRepositoryTest {

    private val database = mock(GithubDatabase::class.java)
    private val repoDao = mock(RepoDao::class.java)
    private val mockGitHubApi = MockGitHubApi().apply { repos = listOf(getRepoResponse()) }

    private lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        whenever(database.repoDao()).doReturn(repoDao)
        repository = GithubRepository(mockGitHubApi, database)
    }

    @Test
    fun `givenDeviceIsConnected verify data isLoadedFrom Remote`() = runBlocking {
        val topRepos = repository.getTopRepos(true)

        verify(database.repoDao()).insertRepos(getRepoEntityList())

        assertThat(topRepos.size).isEqualTo(1)
        assertThat(topRepos).isEqualTo(getRepoEntityList())
    }

    @Test
    fun `givenDeviceIsNotConnected verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getRepos()).doReturn(getRepoEntityList())

        val topRepos = repository.getTopRepos(false)

        verify(database.repoDao()).getRepos()

        assertThat(topRepos.size).isEqualTo(1)
        assertThat(topRepos).isEqualTo(getRepoEntityList())
    }
}