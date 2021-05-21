package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.bookmarks.domain.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToSettingsScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkViewState
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.navigation.ScreenDirections
import com.thomaskioko.stargazer.navigation.ScreenNavigationManager
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
    private val screenNavigationManager: ScreenNavigationManager = mock()
    private val viewModel = GetBookmarkedReposViewModel(
        interactor,
        screenNavigationManager,
        testCoroutineDispatcher
    )

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor()).thenReturn(flowOf(Success(makeRepoViewDataModelList())))

        viewModel.stateFlow.test {

            viewModel.dispatchAction(BookmarkActions.LoadRepositories)

            assertEquals(expectItem(), BookmarkViewState.Loading)
            assertEquals(expectItem(), BookmarkViewState.Success(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor()).thenReturn(flowOf(Error(errorMessage)))

        viewModel.stateFlow.test {

            viewModel.dispatchAction(BookmarkActions.LoadRepositories)

            assertEquals(expectItem(), BookmarkViewState.Loading)
            assertEquals(expectItem(), BookmarkViewState.Error(errorMessage))
        }
    }

    @Test
    fun `when settingsIsPressed verify navigatorIsInvoked`() = runBlocking {
        viewModel.dispatchAction(NavigateToSettingsScreen)

        verify(screenNavigationManager).navigate(ScreenDirections.Settings)
    }

    @Test
    fun `when repoIsClicked verify navigateToDetailIsInvoked`() = runBlocking {
        viewModel.dispatchAction(NavigateToRepoDetailScreen(1L))

        verify(screenNavigationManager).navigate(ScreenDirections.Details)
    }
}
