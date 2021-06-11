package com.thomaskioko.stargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makePagingRepoViewDataModelList
import com.thomaskioko.stargazer.browse.domain.interactor.SearchRepositoriesInteractor
import com.thomaskioko.stargazer.browse.ui.SearchAction
import com.thomaskioko.stargazer.browse.ui.SearchAction.SearchRepository
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule

internal class SearchReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val interactor: SearchRepositoriesInteractor = mock()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val viewModel =
        SearchReposViewModel(interactor, testCoroutineDispatcher)

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        val pagingViewDataList = makePagingRepoViewDataModelList()
        whenever(interactor("Sq")).thenReturn(flowOf(Success(pagingViewDataList)))

        viewModel.stateFlow.test {

            viewModel.dispatchAction(SearchRepository("Sq"))

            assertEquals(expectItem(), SearchViewState.Init)
            assertEquals(expectItem(), SearchViewState.Success(pagingViewDataList))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor("Sq")).thenReturn(flowOf(ViewStateResult.Error(errorMessage)))

        viewModel.stateFlow.test {

            viewModel.dispatchAction(SearchRepository("Sq"))

            assertEquals(expectItem(), SearchViewState.Init)
            assertEquals(expectItem(), SearchViewState.Error(errorMessage))
        }
    }

    @Test
    fun `when backIsPressed verify navigatorIsInvoked`() = runBlocking {
        viewModel.dispatchAction(SearchAction.BackPressed)

        // TODO:: User screenNavigator to go back verify(screenNavigator).goBack()
    }

    @Test
    fun `when repoIsClicked verify navigateToDetailIsInvoked`() = runBlocking {
        viewModel.dispatchAction(SearchAction.NavigateToRepoDetailScreen(1L))

//        verify(screenNavigator).navigate(ScreenDirections.Details)
    }
}
