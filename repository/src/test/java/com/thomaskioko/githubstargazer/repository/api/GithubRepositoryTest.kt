package com.thomaskioko.githubstargazer.repository.api

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoResponse
import com.thomaskioko.githubstargazer.repository.util.MockGitHubApi
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
    private val mockGitHubApi = MockGitHubApi().apply { repos = listOf(makeRepoResponse()) }

    private lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        whenever(database.repoDao()).doReturn(repoDao)
        repository = GithubRepository(mockGitHubApi, database)
    }

    @Test
    fun `givenDeviceIsConnected verify data isLoadedFrom Remote`() = runBlocking {
        whenever(repoDao.getRepos()).doReturn(makeRepoEntityList())

        val repos = repository.getRepos(true)

        verify(database.repoDao()).insertRepos(makeRepoEntityList())

        assertThat(repos.size).isEqualTo(1)
        assertThat(repos).isEqualTo(makeRepoEntityList())
    }

    @Test
    fun `givenDeviceIsNotConnected verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getRepos()).doReturn(makeRepoEntityList())

        val repos = repository.getRepos(false)

        verify(database.repoDao()).getRepos()

        assertThat(repos.size).isEqualTo(1)
        assertThat(repos).isEqualTo(makeRepoEntityList())
    }
}