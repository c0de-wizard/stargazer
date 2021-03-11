package com.thomaskioko.stargazer.browse.domain.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Mockito

internal class GetRepoListInteractorTest {

    private val repository: GithubRepository = Mockito.mock(GithubRepository::class.java)
    private val interactor = GetRepoListInteractor(repository)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepositoryList(anyBoolean())).thenReturn(flowOf(makeRepoEntityList()))

        interactor(true).test {
            assertEquals(expectItem(), ViewStateResult.Success(makeRepoViewDataModelList()))
            expectComplete()
        }
    }

    @Test
    fun givenAnErrorOccurs_ExceptionIsThrown() = runBlocking {
        val errorMessage = "Something went wrong"
        whenever(repository.getRepositoryList(true))
            .thenReturn(flow { throw Exception(errorMessage) })

        val errorResult = Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage)

        interactor(true).test {
            assertEquals(expectItem(), errorResult)
            expectComplete()
        }
    }
}
