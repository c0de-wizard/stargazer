package com.thomaskioko.stargazer.trending.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.navigation.ScreenDirections
import com.thomaskioko.stargazer.navigation.ScreenNavigationManager
import com.thomaskioko.stargazer.trending.interactor.GetTrendingReposInteractor
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makePagingRepoViewDataModelList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule

internal class TrendingRepoListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val interactorTrending: GetTrendingReposInteractor = mock()
    private val screenNavigator: ScreenNavigationManager = mock()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val viewModel =
        TrendingRepoListViewModel(interactorTrending, screenNavigator, testCoroutineDispatcher)

    @Test
    fun givenDisplayStateIsInvoked_verifyResultRepoListIsReturned() = runBlocking {
        val pagingViewDataList = makePagingRepoViewDataModelList()
        whenever(interactorTrending()).thenReturn(flowOf(Success(pagingViewDataList)))

        viewModel.stateFlow.test {
            viewModel.dispatchAction(ReposAction.LoadRepositories)

            assertEquals(expectItem(), ReposViewState.Loading)
            assertEquals(expectItem(), ReposViewState.Success(pagingViewDataList))
        }
    }

    @Test
    fun givenFailureResponse_verifyErrorStateIsReturned() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactorTrending()).thenReturn(flowOf(Error(errorMessage)))

        viewModel.stateFlow.test {
            viewModel.dispatchAction(ReposAction.LoadRepositories)

            assertEquals(expectItem(), ReposViewState.Loading)
            assertEquals(expectItem(), ReposViewState.Error(errorMessage))
        }
    }


    @Test
    fun `when settingsIsPressed verify navigatorIsInvoked`() = runBlocking {
        viewModel.dispatchAction(ReposAction.NavigateToSettingsScreen)

        verify(screenNavigator).navigate(ScreenDirections.Settings)
    }

    @Test
    fun `when repoIsClicked verify navigateToDetailIsInvoked`() = runBlocking {
        viewModel.dispatchAction(ReposAction.NavigateToRepoDetailScreen(1L))

        verify(screenNavigator).navigate(ScreenDirections.Details)
    }
}
