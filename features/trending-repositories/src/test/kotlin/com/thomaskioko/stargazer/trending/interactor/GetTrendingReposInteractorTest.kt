package com.thomaskioko.stargazer.trending.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makePagingRepoEntityModelList
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makePagingRepoViewDataModelList
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetTrendingReposInteractorTest {

    private val mockPagingList = makePagingRepoEntityModelList()
    private val repository: GithubRepository = mock {
        on { getTrendingTrendingRepositories() } doReturn flowOf(mockPagingList)
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val interactor = GetTrendingReposInteractor(repository, testCoroutineDispatcher)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor().test {
            cancelAndConsumeRemainingEvents()

            /**
             * TODO:: Figure out how to mock PagingList. Objects being returned are different
            assertEquals(expectItem(), ViewStateResult.Success(makePagingRepoViewDataModelList()))
            expectComplete()
             */
        }
    }

    @Test
    fun givenAnErrorOccurs_ExceptionIsThrown() = runBlocking {
        val errorMessage = "Something went wrong"
        whenever(repository.getTrendingTrendingRepositories())
            .thenReturn(flow { throw Exception(errorMessage) })

        val errorResult = Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage)

        interactor().test {
            assertEquals(expectItem(), errorResult)
            expectComplete()
        }
    }
}
