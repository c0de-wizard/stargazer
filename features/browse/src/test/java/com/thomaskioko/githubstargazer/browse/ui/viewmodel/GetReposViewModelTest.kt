package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.ViewState.Error
import com.thomaskioko.githubstargazer.core.ViewState.Loading
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.anyBoolean
import org.mockito.Mockito.anyLong
import org.mockito.MockitoAnnotations

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
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlockingTest {
        val flow = flow {
            emit(Loading())
            delay(10)
            emit(ViewState.Success(makeRepoViewDataModelList()))
        }

        whenever(interactor(anyBoolean())).doReturn(flow)

        val list = viewModel.getRepos().toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is success
        assertThat(list.last()).isEqualTo(ViewState.success(makeRepoViewDataModelList()))
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlockingTest {
        val errorMessage = "Something went wrong"

        val flow: Flow<ViewState<List<RepoViewDataModel>>> = flow {
            emit(Loading())
            delay(10)
            emit(Error(errorMessage))
        }

        whenever(interactor(anyBoolean())).doReturn(flow)

        val list = viewModel.getRepos().toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is Error
        assertThat(list.last()).isEqualTo(Error<ViewState<RepoViewDataModel>>(errorMessage))
    }

    @Test
    fun `givenRepoId verify successStateIsReturned`() = runBlockingTest {
        val repoViewDataModel = makeRepoViewDataModelList()[0]
        val flow = flow {
            emit(Loading())
            delay(10)
            emit(ViewState.Success(repoViewDataModel))
        }

        whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

        val list = viewModel.getRepoById().toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is Error
        assertThat(list.last()).isEqualTo(ViewState.success(repoViewDataModel))
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() = runBlockingTest {

        val errorMessage = "Something went wrong"

        val flow: Flow<ViewState<RepoViewDataModel>> = flow {
            emit(Loading())
            delay(10)
            emit(Error(errorMessage))
        }

        whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

        val list = viewModel.getRepoById().toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is Error
        assertThat(list.last()).isEqualTo(Error<ViewState<RepoViewDataModel>>(errorMessage))
    }

    @InternalCoroutinesApi
    @Test
    fun `givenUpdateRepoIsInvoked verify successStateIsReturned`() = runBlockingTest {
        val updateObject = UpdateObject(1, true)
        val repoViewDataModel = makeRepoViewDataModelList()[0]

        val flow = flow {
            emit(Loading())
            delay(10)
            emit(ViewState.Success(repoViewDataModel))
        }

        whenever(bookmarkStateInteractor(updateObject)).doReturn(flow)

        val list = viewModel.updateBookmarkState(updateObject).toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is Success
        assertThat(list.last()).isEqualTo(ViewState.Success(repoViewDataModel))
    }
}
