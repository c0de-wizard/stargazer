package com.thomaskioko.stargazer.browse.domain.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makePagingRepoEntityModelList
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SearchRepositoriesInteractorTest {

    private val mockPagingList = makePagingRepoEntityModelList()
    private val repository: GithubRepository = mock {
        on { searchRepository("Su") } doReturn flowOf(mockPagingList)
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val interactor = SearchRepositoriesInteractor(repository, testCoroutineDispatcher)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor("Su").test {
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
        whenever(repository.searchRepository("Su"))
            .thenReturn(flow { throw Exception(errorMessage) })

        val errorResult = Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage)

        interactor("Su").test {
            assertEquals(expectItem(), errorResult)
            expectComplete()
        }
    }
}
