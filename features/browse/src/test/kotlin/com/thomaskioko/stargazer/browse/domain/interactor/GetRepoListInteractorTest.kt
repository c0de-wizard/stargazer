package com.thomaskioko.stargazer.browse.domain.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean

internal class GetRepoListInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetRepoListInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepositoryList(anyBoolean())).doReturn(flowOf(makeRepoEntityList()))

        val result = interactor(true).toList()
        val expected = listOf(
            ViewStateResult.Success(makeRepoViewDataModelList())
        )

        assertThat(result).isEqualTo(expected)
    }
}
