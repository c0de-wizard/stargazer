package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.bookmarks.data.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.ViewState.*
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
internal class GetBookmarkedReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val interactor: GetBookmarkedRepoListInteractor = mock()
    private lateinit var viewModel: GetBookmarkedReposViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetBookmarkedReposViewModel(interactor)
    }

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlockingTest {
        val flow = flow {
            emit(Loading())
            delay(10)
            emit(Success(makeRepoViewDataModelList()))
        }

        whenever(interactor()).doReturn(flow)

        val list = viewModel.getBookmarkedRepos().toList()

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

        whenever(interactor()).doReturn(flow)

        val list = viewModel.getBookmarkedRepos().toList()

        // Check whether first data is loading
        assertThat(list.first()).isEqualTo(Loading<ViewState<RepoViewDataModel>>())

        // Check second/last item is Error
        assertThat(list.last()).isEqualTo(
            Error<ViewState<RepoViewDataModel>>(errorMessage)
        )
    }
}
