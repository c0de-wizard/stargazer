package com.thomaskioko.stargazer.trending.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.trending.interactor.GetTrendingReposInteractor
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoViewDataModelList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule

internal class GetRepoListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val interactorTrending: GetTrendingReposInteractor = mock()
    private val screenNavigator: ScreenNavigator = mock()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val viewModel =
        GetRepoListViewModel(interactorTrending, screenNavigator, testCoroutineDispatcher)

    @Test
    fun givenDisplayStateIsInvoked_verifyResultRepoListIsReturned() = runBlocking {
        whenever(interactorTrending()).thenReturn(flowOf(Success(makeRepoViewDataModelList())))

        viewModel.actionState.test {
            viewModel.dispatchAction(ReposAction.LoadRepositories)

            assertEquals(expectItem(), ReposViewState.Loading)
            assertEquals(expectItem(), ReposViewState.ResultRepoList(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun givenFailureResponse_verifyErrorStateIsReturned() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactorTrending()).thenReturn(flowOf(Error(errorMessage)))

        viewModel.actionState.test {
            viewModel.dispatchAction(ReposAction.LoadRepositories)

            assertEquals(expectItem(), ReposViewState.Loading)
            assertEquals(expectItem(), ReposViewState.Error(errorMessage))
        }
    }
}
