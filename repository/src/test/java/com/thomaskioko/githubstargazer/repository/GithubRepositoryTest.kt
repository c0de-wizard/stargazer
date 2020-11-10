package com.thomaskioko.githubstargazer.repository

import com.thomaskioko.githubstargazer.repository.MockData.getRepoResponse
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import org.junit.Before
import org.junit.Test

class GithubRepositoryTest {

    private lateinit var appRepository: GithubRepository

    private val mockGitHubApi = MockGitHubApi().apply { repos = listOf(getRepoResponse()) }

    @Before
    fun setUp() {
        appRepository = GithubRepository(mockGitHubApi)
    }

    @Test
    fun `givenQueryIsSuccess verify data is returned`() = runBlocking {
        val topRepos = appRepository.getTopRepos()

        assertThat(topRepos.items.size).isEqualTo(1)
        assertThat(topRepos.items[0]).isEqualTo(getRepoResponse())
    }

}