package com.thomaskioko.stargazer.browse.domain.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito

internal class SearchRepositoriesInteractorTest {

    private val repository: GithubRepository = Mockito.mock(GithubRepository::class.java)
    private val interactor = SearchRepositoriesInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.searchRepository("Su")).thenReturn(flowOf(makeRepoEntityList()))

        interactor("Su").test {
            assertEquals(expectItem(), ViewStateResult.Success(makeRepoViewDataModelList()))
            expectComplete()
        }
    }

    @Test
    fun givenAnErrorOccurs_ExceptionIsThrown() = runBlocking {
        val errorMessage = "Something went wrong"
        whenever(repository.searchRepository("Su"))
            .thenReturn(flow { throw Exception(errorMessage) })

        val errorResult = Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage)

        interactor("Su").test {
            assertEquals(expectItem(), errorResult)
            expectComplete()
        }
    }
}
