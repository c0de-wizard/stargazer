package com.thomaskioko.stargazer.repo_details.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.ViewStateResult.Error
import com.thomaskioko.stargazer.core.ViewStateResult.Loading
import com.thomaskioko.stargazer.core.ViewStateResult.Success
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
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.anyLong
import org.mockito.MockitoAnnotations

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

    @Test
    fun `givenRepoId verify successStateIsReturned`() = runBlocking {
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoMutableStateResultFlow.test {

            viewModel.getRepoById(1)

            assertEquals(expectItem(), Loading<ViewStateResult<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() = runBlocking {

        val errorMessage = "Something went wrong"

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Error(errorMessage))

        viewModel.repoMutableStateResultFlow.test {

            viewModel.getRepoById(1)

            assertEquals(expectItem(), Loading<ViewStateResult<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewStateResult<List<RepoViewDataModel>>>(errorMessage))
        }
    }

    @Test
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlocking {
        val updateObject =
            UpdateObject(1, true)
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(bookmarkStateInteractor(updateObject)) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoUpdateMutableStateResultFlow.test {

            viewModel.updateBookmarkState(updateObject)

            assertEquals(expectItem(), Loading<ViewStateResult<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }
}
