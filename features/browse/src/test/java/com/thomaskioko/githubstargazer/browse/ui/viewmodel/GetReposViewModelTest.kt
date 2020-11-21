package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.domain.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.ViewState.*
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        val flow = flow {
            emit(Loading())
            delay(10)
            emit(Success(makeRepoViewDataModelList()))
        }

        whenever(interactor(anyBoolean())) doReturn (flow)

        viewModel.getRepos().test {
            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(makeRepoViewDataModelList()))
            expectNoEvents()
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        val flow: Flow<ViewState<List<RepoViewDataModel>>> = flow {
            emit(Loading())
            delay(10)
            emit(Error(errorMessage))
        }

        whenever(interactor(anyBoolean())).doReturn(flow)

        viewModel.getRepos().test {
            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<RepoViewDataModel>>(errorMessage))
            expectNoEvents()
        }
    }

    @Test
    fun `givenRepoId verify successStateIsReturned`() = runBlocking {
        val repoViewDataModel = makeRepoViewDataModelList()[0]
        val flow = flow {
            emit(Loading())
            delay(10)
            emit(Success(repoViewDataModel))
        }

        whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

        viewModel.getRepoById().test {
            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
            expectNoEvents()
        }
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() = runBlocking {

        val errorMessage = "Something went wrong"

        val flow: Flow<ViewState<RepoViewDataModel>> = flow {
            emit(Loading())
            delay(10)
            emit(Error(errorMessage))
        }

        whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

        viewModel.getRepoById().test {
            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<List<RepoViewDataModel>>>(errorMessage))
            expectNoEvents()
        }
    }

    @InternalCoroutinesApi
    @Test
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlocking {
        val updateObject = UpdateObject(1, true)
        val repoViewDataModel = makeRepoViewDataModelList()[0]

        val flow = flow {
            emit(Loading())
            delay(10)
            emit(Success(repoViewDataModel))
        }

        whenever(bookmarkStateInteractor(updateObject)).doReturn(flow)

        viewModel.updateBookmarkState(updateObject).test {
            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(repoViewDataModel))
            expectNoEvents()
        }
    }
}
