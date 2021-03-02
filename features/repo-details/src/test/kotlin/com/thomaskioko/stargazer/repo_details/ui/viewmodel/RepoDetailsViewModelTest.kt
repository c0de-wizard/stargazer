package com.thomaskioko.stargazer.repo_details.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewState
import com.thomaskioko.stargazer.core.ViewState.Error
import com.thomaskioko.stargazer.core.ViewState.Loading
import com.thomaskioko.stargazer.core.ViewState.Success
import com.thomaskioko.stargazer.repo_details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.repo_details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.repo_details.domain.model.UpdateObject
import com.thomaskioko.stargazer.repo_details.model.RepoViewDataModel
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.testing.CoroutineScopeRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mockito.anyLong
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal class RepoDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val scopeRule = CoroutineScopeRule()

    private val getRepoByIdInteractor: GetRepoByIdInteractor = mock()
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor = mock()

    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = RepoDetailsViewModel(
            getRepoByIdInteractor,
            bookmarkStateInteractor
        )
    }

    // @Test TODO: fix failing test on CI
    fun `givenRepoId verify successStateIsReturned`() = runBlocking {
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoMutableStateFlow.test {

            viewModel.getRepoById(anyLong())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }

    // @Test TODO: fix failing test on CI
    fun `givenFailureById verify errorStateIsReturned`() = runBlocking {

        val errorMessage = "Something went wrong"

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Error(errorMessage))

        viewModel.repoMutableStateFlow.test {

            viewModel.getRepoById(anyLong())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<List<RepoViewDataModel>>>(errorMessage))
        }
    }

    // @Test TODO: fix failing test on CI
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlocking {
        val updateObject =
            UpdateObject(1, true)
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(bookmarkStateInteractor(updateObject)) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoUpdateMutableStateFlow.test {

            viewModel.updateBookmarkState(updateObject)

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }
}
