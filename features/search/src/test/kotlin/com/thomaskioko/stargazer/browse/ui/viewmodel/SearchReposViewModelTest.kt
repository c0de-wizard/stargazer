package com.thomaskioko.stargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.browse.domain.interactor.SearchRepositoriesInteractor
import com.thomaskioko.stargazer.browse.ui.SearchAction
import com.thomaskioko.stargazer.browse.ui.SearchAction.SearchRepository
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
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
    private val screenNavigator: ScreenNavigator = mock()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val viewModel =
        SearchReposViewModel(interactor, screenNavigator, testCoroutineDispatcher)

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor("Sq")).thenReturn(flowOf(Success(makeRepoViewDataModelList())))

        viewModel.stateFlow.test {

            viewModel.dispatchAction(SearchRepository("Sq"))

            assertEquals(expectItem(), SearchViewState.Init)
            assertEquals(expectItem(), SearchViewState.Success(makeRepoViewDataModelList()))
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

        verify(screenNavigator).goBack()
    }

    @Test
    fun `when repoIsClicked verify navigateToDetailIsInvoked`() = runBlocking {
        viewModel.dispatchAction(SearchAction.NavigateToRepoDetailScreen(1L))

        verify(screenNavigator).goToScreen(NavigationScreen.RepoDetailsScreen(1L))
    }
}
