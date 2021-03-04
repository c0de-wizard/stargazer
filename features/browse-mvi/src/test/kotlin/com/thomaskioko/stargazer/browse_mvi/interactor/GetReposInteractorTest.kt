package com.thomaskioko.stargazer.browse_mvi.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse_mvi.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.browse_mvi.interactor.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class GetReposInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetReposInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepositoryList(true))
            .thenReturn(flowOf(makeRepoEntityList()))

        val result = interactor(true).toList()
        val expected = listOf(
            ViewStateResult.Success(makeRepoViewDataModelList())
        )

        assertThat(result).isEqualTo(expected)
    }
}
