package com.thomaskioko.githubstargazer.browse.domain.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.domain.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetRepoListInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetRepoListInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepos(true)).doReturn(makeRepoEntityList())

        val result = interactor(true).toList()
        val expected = listOf(
            ViewState.Loading(),
            ViewState.Success(makeRepoViewDataModelList())
        )

        assertThat(result).isEqualTo(expected)
    }
}
