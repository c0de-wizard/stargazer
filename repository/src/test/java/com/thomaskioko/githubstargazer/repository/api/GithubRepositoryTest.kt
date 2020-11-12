package com.thomaskioko.githubstargazer.repository.api

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.dao.RepoDao
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoResponse
import com.thomaskioko.githubstargazer.repository.util.MockGitHubApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

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
    fun `givenDeviceIsConnected andDatabaseIsEmpty verify data isLoadedFrom Remote`() = runBlocking {
        whenever(repoDao.getRepos()).doReturn(emptyList())
        whenever(repository.getRepos(true)).doReturn(makeRepoEntityList())

        val repos = repository.getRepos(true)

        verify(database.repoDao()).insertRepos(makeRepoEntityList())

        assertThat(repos.size).isEqualTo(1)
        assertThat(repos).isEqualTo(makeRepoEntityList())
    }

    @Test
    fun `givenDeviceIsNotConnected verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getRepos()).doReturn(makeRepoEntityList())

        val repos = repository.getRepos(false)

        verify(database.repoDao(), times(2)).getRepos()

        assertThat(repos.size).isEqualTo(1)
        assertThat(repos).isEqualTo(makeRepoEntityList())
    }

    @Test
    fun `givenRepoId verify data isLoadedFrom Database`() = runBlocking {
        val expected = makeRepoEntityList()[0]

        whenever(repoDao.getRepoById(expected.repoId)).doReturn(expected)

        val repoEntity = repository.getRepoById(expected.repoId)

        verify(database.repoDao()).getRepoById(expected.repoId)

        assertThat(repoEntity).isEqualTo(expected)
    }

    @Test
    fun `wheneverGetBookmarkedRepos verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getBookmarkedRepos()).doReturn(makeRepoEntityList())

        val repoEntity = repository.getBookmarkedRepos()

        verify(database.repoDao()).getBookmarkedRepos()

        assertThat(repoEntity).isEqualTo(makeRepoEntityList())
    }

    @Test
    fun `wheneverUpdateRepo verify data isLoadedFrom Database`() = runBlocking {
        val entity = makeRepoEntityList()[0]

        whenever(repoDao.setBookmarkStatus(anyInt(), anyLong())).doReturn(Unit)
        whenever(repoDao.getRepoById(anyLong())).doReturn(entity)

        repoDao.insertRepo(entity)

        repository.updateRepoBookMarkStatus(1, entity.repoId)

        val repoEntity = repository.getRepoById(entity.repoId)

        verify(database.repoDao()).getRepoById(entity.repoId)

        assertThat(repoEntity).isEqualTo(entity)
        assertThat(repoEntity.isBookmarked).isEqualTo(entity.isBookmarked)
    }
}