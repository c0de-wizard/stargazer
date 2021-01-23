package com.thomaskioko.githubstargazer.browse_mvi.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse_mvi.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.browse_mvi.interactor.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean

internal class GetReposInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetReposInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepositoryList(anyBoolean())) doReturn flowOf(makeRepoEntityList())

        val result = interactor(true).toList()
        val expected = listOf(
            ViewState.Success(makeRepoViewDataModelList())
        )

        assertThat(result).isEqualTo(expected)
    }
}