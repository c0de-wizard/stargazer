package com.thomaskioko.stargazer.repo_details.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.ui.DetailAction
import com.thomaskioko.stargazer.details.ui.DetailViewState
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeUpdatedRepo
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentMatchers.anyLong

internal class RepoDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val updateObject = makeUpdatedRepo()
    private val repoViewModelData = makeRepoViewDataModel()
    private val screenNavigator = mock<ScreenNavigator>()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val getRepoByIdInteractor: GetRepoByIdInteractor = mock {
        on { invoke(anyLong()) } doReturn flowOf(Success(repoViewModelData))
    }
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor = mock {
        on { invoke(updateObject) } doReturn flowOf(Success(repoViewModelData))
    }

    private var viewModel: RepoDetailsViewModel = RepoDetailsViewModel(
        getRepoByIdInteractor,
        bookmarkStateInteractor,
        screenNavigator,
        testCoroutineDispatcher
    )

    @Test
    fun `givenRepoId verify successStateIsReturned`() = runBlocking {
        val repoViewDataModel = makeRepoViewDataModel()

        viewModel.actionState.test {

            viewModel.dispatchAction(DetailAction.LoadRepo(1))

            assertEquals(expectItem(), DetailViewState.Loading)
            assertEquals(expectItem(), DetailViewState.Success(repoViewDataModel))
        }
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() = runBlocking {

        val errorMessage = "Something went wrong"

        whenever(getRepoByIdInteractor(1)).thenReturn(flowOf(Error(errorMessage)))

        viewModel.actionState.test {

            viewModel.dispatchAction(DetailAction.LoadRepo(1))

            assertEquals(expectItem(), DetailViewState.Loading)
            assertEquals(expectItem(), DetailViewState.Error(errorMessage))
        }
    }

    @Test
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlocking {
        viewModel.actionState.test {

            viewModel.dispatchAction(DetailAction.UpdateRepo(updateObject))

            assertEquals(expectItem(), DetailViewState.Loading)
            assertEquals(expectItem(), DetailViewState.Success(repoViewModelData))
        }
    }
}
