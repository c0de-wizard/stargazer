package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.bookmarks.domain.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Loading
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule

internal class GetBookmarkedReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val interactor: GetBookmarkedRepoListInteractor = mock()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val viewModel = GetBookmarkedReposViewModel(interactor, testCoroutineDispatcher)

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor()).thenReturn(flowOf(Success(makeRepoViewDataModelList())))

        viewModel.bookmarkedList.test {

            viewModel.getBookmarkedRepos()

            assertEquals(expectItem(), Loading<ViewStateResult<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor()).thenReturn(flowOf(Error(errorMessage)))

        viewModel.bookmarkedList.test {

            viewModel.getBookmarkedRepos()

            assertEquals(expectItem(), Loading<ViewStateResult<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewStateResult<RepoViewDataModel>>(errorMessage))
        }
    }
}
