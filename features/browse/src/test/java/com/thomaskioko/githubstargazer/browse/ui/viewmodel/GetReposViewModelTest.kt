package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.browse.ui.util.CoroutineScopeRule
import com.thomaskioko.githubstargazer.core.ViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
internal class GetReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = CoroutineScopeRule()

    @Captor
    private lateinit var captorList: ArgumentCaptor<ViewState<List<RepoViewDataModel>>>

    @Captor
    private lateinit var captor: ArgumentCaptor<ViewState<RepoViewDataModel>>

    private val interactor: GetRepoListInteractor = mock()
    private val getRepoByIdInteractor: GetRepoByIdInteractor = mock()
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor = mock()
    private val stateObserverList: Observer<ViewState<List<RepoViewDataModel>>> = mock()
    private val stateObserver: Observer<ViewState<RepoViewDataModel>> = mock()

    private lateinit var viewModel: GetReposViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetReposViewModel(interactor, getRepoByIdInteractor, bookmarkStateInteractor)
    }

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() {
        coroutineScope.runBlockingTest {
            val flow = flow {
                emit(ViewState.Loading())
                delay(10)
                emit(ViewState.Success(makeRepoViewDataModelList()))
            }

            whenever(interactor(anyBoolean())).doReturn(flow)

            viewModel.getRepos().observeForever(stateObserverList) // Trigger the transformation

            verify(stateObserverList).onChanged(captorList.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserverList, times(2))
                .onChanged(captorList.capture()) // onchange has been triggered twice

            verify(stateObserverList).onChanged(ViewState.success(makeRepoViewDataModelList()))

            val state = (captorList.value as ViewState.Success<List<RepoViewDataModel>>)
            val viewDataModel = state.data[0]

            assertThat(viewDataModel.name).isEqualTo("Square")
            assertThat(viewDataModel).isEqualTo(makeRepoViewDataModelList()[0])

            viewModel.getRepos().removeObserver(stateObserverList)
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() {
        coroutineScope.runBlockingTest {
            val errorMessage = "Something went wrong"

            val flow: Flow<ViewState<List<RepoViewDataModel>>> = flow {
                emit(ViewState.Loading())
                delay(10)
                emit(ViewState.Error(errorMessage))
            }

            whenever(interactor(anyBoolean())).doReturn(flow)

            viewModel.getRepos().observeForever(stateObserverList) // Trigger the transformation

            verify(stateObserverList).onChanged(captorList.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserverList, times(2))
                .onChanged(captorList.capture()) // onchange has been triggered twice

            verify(stateObserverList).onChanged(ViewState.Error(errorMessage))

            viewModel.getRepos().removeObserver(stateObserverList)
        }
    }

    @Test
    fun `givenRepoId verify successStateIsReturned`() {
        coroutineScope.runBlockingTest {
            val repoViewDataModel = makeRepoViewDataModelList()[0]
            val flow = flow {
                emit(ViewState.Loading())
                delay(10)
                emit(ViewState.Success(repoViewDataModel))
            }

            whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

            viewModel.getRepoById().observeForever(stateObserver) // Trigger the transformation

            verify(stateObserver).onChanged(captor.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserver, times(2))
                .onChanged(captor.capture()) // onchange has been triggered twice

            verify(stateObserver).onChanged(ViewState.success(repoViewDataModel))

            val state = (captor.value as ViewState.Success<RepoViewDataModel>)
            val viewDataModel = state.data

            assertThat(viewDataModel.name).isEqualTo("Square")
            assertThat(viewDataModel).isEqualTo(makeRepoViewDataModelList()[0])

            viewModel.getRepoById().removeObserver(stateObserver)
        }
    }

    @Test
    fun `givenFailureById verify errorStateIsReturned`() {
        coroutineScope.runBlockingTest {
            val errorMessage = "Something went wrong"

            val flow: Flow<ViewState<RepoViewDataModel>> = flow {
                emit(ViewState.Loading())
                delay(10)
                emit(ViewState.Error(errorMessage))
            }

            whenever(getRepoByIdInteractor(anyLong())).doReturn(flow)

            viewModel.getRepoById().observeForever(stateObserver) // Trigger the transformation

            verify(stateObserver).onChanged(captor.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserver, times(2))
                .onChanged(captor.capture()) // onchange has been triggered twice

            verify(stateObserver).onChanged(ViewState.Error(errorMessage))

            viewModel.getRepoById().removeObserver(stateObserver)
        }
    }
}
