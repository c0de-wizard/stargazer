package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
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
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
internal class GetReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = CoroutineScopeRule()

    @Captor
    private lateinit var captor: ArgumentCaptor<ViewState<List<RepoViewDataModel>>>

    private val interactor: GetRepoListInteractor = mock()
    private val stateObserver: Observer<ViewState<List<RepoViewDataModel>>> = mock()

    private lateinit var viewModel: GetReposViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetReposViewModel(interactor)
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

            viewModel.getRepos().observeForever(stateObserver) // Trigger the transformation

            verify(stateObserver).onChanged(captor.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserver, Mockito.times(2))
                .onChanged(captor.capture()) // onchange has been triggered twice

            verify(stateObserver).onChanged(ViewState.success(makeRepoViewDataModelList()))

            val state = (captor.value as ViewState.Success<List<RepoViewDataModel>>)
            val viewDataModel = state.data[0]

            assertThat(viewDataModel.name).isEqualTo("Square")
            assertThat(viewDataModel).isEqualTo(makeRepoViewDataModelList()[0])

            viewModel.getRepos().removeObserver(stateObserver)
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

            viewModel.getRepos().observeForever(stateObserver) // Trigger the transformation

            verify(stateObserver).onChanged(captor.capture()) // loading state has been received

            coroutineScope.advanceTimeBy(10)

            verify(stateObserver, Mockito.times(2))
                .onChanged(captor.capture()) // onchange has been triggered twice

            verify(stateObserver).onChanged(ViewState.Error(errorMessage))

            viewModel.getRepos().removeObserver(stateObserver)
        }
    }
}
