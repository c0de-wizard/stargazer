package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.domain.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.githubstargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.domain.model.UpdateObject
import com.thomaskioko.githubstargazer.browse.ui.util.CoroutineScopeRule
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.ViewState.*
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyLong
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class GetReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val scopeRule = CoroutineScopeRule()

    private val interactor: GetRepoListInteractor = mock()
    private val getRepoByIdInteractor: GetRepoByIdInteractor = mock()
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor = mock()

    private lateinit var viewModel: GetReposViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetReposViewModel(interactor, getRepoByIdInteractor, bookmarkStateInteractor)
    }

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor(anyBoolean())) doReturn flowOf(Success(makeRepoViewDataModelList()))

        viewModel.repoList.test {

            viewModel.getRepoList(anyBoolean())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor(anyBoolean())) doReturn flowOf(Error(errorMessage))

        viewModel.repoList.test {

            viewModel.getRepoList(anyBoolean())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<RepoViewDataModel>>(errorMessage))
        }
    }

    @Test
    fun `givenRepoId verify successStateIsReturned`() = runBlocking {
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoMutableStateFlow.test {

            viewModel.getRepoById(anyLong())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() = runBlocking {

        val errorMessage = "Something went wrong"

        whenever(getRepoByIdInteractor(anyLong())) doReturn flowOf(Error(errorMessage))

        viewModel.repoMutableStateFlow.test {

            viewModel.getRepoById(anyLong())

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<List<RepoViewDataModel>>>(errorMessage))
        }
    }

    @Test
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlocking {
        val updateObject = UpdateObject(1, true)
        val repoViewDataModel = makeRepoViewDataModel()

        whenever(bookmarkStateInteractor(updateObject)) doReturn flowOf(Success(repoViewDataModel))

        viewModel.repoUpdateMutableStateFlow.test {

            viewModel.updateBookmarkState(updateObject)

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
        }
    }
}
