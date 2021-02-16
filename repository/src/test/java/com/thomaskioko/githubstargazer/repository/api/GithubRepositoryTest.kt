package com.thomaskioko.githubstargazer.repository.api

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.repository.api.service.GitHubService
import com.thomaskioko.githubstargazer.repository.db.GithubDatabase
import com.thomaskioko.githubstargazer.repository.db.dao.RepoDao
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.repository.util.MockData.makeRepoResponseList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import kotlin.time.ExperimentalTime

class GithubRepositoryTest {

    private val database = mock(GithubDatabase::class.java)
    private val service = mock(GitHubService::class.java)
    private val repoDao = mock(RepoDao::class.java)

    private lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        whenever(database.repoDao()).doReturn(repoDao)
        repository = GithubRepository(service, database)
    }

    @ExperimentalCoroutinesApi
    @ExperimentalTime
    @Test
    fun `givenDeviceIsConnected verify data isLoadedFrom Remote`() = runBlocking {
        whenever(repoDao.getReposFlow()) doReturn flowOf(emptyList())
        whenever(service.getRepositories()) doReturn makeRepoResponseList()

        // TODO:: Replace with Turbine Test
        val repos = repository.getRepositoryList(true).toList()
        val expected = listOf(makeRepoEntityList())

        verify(service).getRepositories()
        verify(database.repoDao()).insertRepos(makeRepoEntityList())
        verify(database.repoDao(), times(2)).getReposFlow()

        assertThat(repos.size).isEqualTo(expected.size)
    }

    @Test
    fun `givenDeviceIsNotConnected verify data isLoadedFrom Database`() = runBlocking {

        whenever(repoDao.getReposFlow()).doReturn(flowOf(makeRepoEntityList()))

        val repos = repository.getRepositoryList(false).toList()
        val expected = listOf(makeRepoEntityList())

        verify(service, never()).getRepositories()
        verify(database.repoDao(), times(2)).getReposFlow()

        assertThat(repos).isEqualTo(expected)
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
