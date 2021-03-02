package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewState
import com.thomaskioko.stargazer.core.ViewState.Error
import com.thomaskioko.stargazer.core.ViewState.Loading
import com.thomaskioko.stargazer.core.ViewState.Success
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.testing.CoroutineScopeRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.MockitoAnnotations
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class GetBookmarkedReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val scopeRule = CoroutineScopeRule()

    private val interactor: GetBookmarkedRepoListInteractor = mock()
    private lateinit var viewModel: GetBookmarkedReposViewModel

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)

        viewModel = GetBookmarkedReposViewModel(interactor)
    }

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor()) doReturn flowOf(Success(makeRepoViewDataModelList()))

        viewModel.bookmarkedList.test {

            viewModel.getBookmarkedRepos()

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor()) doReturn flowOf(Error(errorMessage))

        viewModel.bookmarkedList.test {

            viewModel.getBookmarkedRepos()

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<RepoViewDataModel>>(errorMessage))
        }
    }
}
