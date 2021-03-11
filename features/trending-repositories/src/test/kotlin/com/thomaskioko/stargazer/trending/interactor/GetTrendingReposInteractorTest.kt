package com.thomaskioko.stargazer.trending.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.trending.model.RepoViewDataModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyBoolean
import java.lang.Exception

internal class GetTrendingReposInteractorTest {

    private val repository: GithubRepository = mock {
        on { getTrendingTrendingRepositories(anyBoolean()) } doReturn flowOf(makeRepoEntityList())
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val interactor = GetTrendingReposInteractor(repository, testCoroutineDispatcher)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor(true).test {
            assertEquals(expectItem(), ViewStateResult.Success(makeRepoViewDataModelList()))
            expectComplete()
        }
    }

    @Test
    fun givenAnErrorOccurs_ExceptionIsThrown() = runBlocking {
        val errorMessage = "Something went wrong"
        whenever(repository.getTrendingTrendingRepositories(true))
            .thenReturn(flow { throw Exception(errorMessage) })

        val errorResult = Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage)

        interactor(true).test {
            assertEquals(expectItem(), errorResult)
            expectComplete()
        }
    }
}
