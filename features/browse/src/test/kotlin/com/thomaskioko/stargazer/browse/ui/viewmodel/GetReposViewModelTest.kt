package com.thomaskioko.stargazer.browse.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.browse.domain.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewState
import com.thomaskioko.stargazer.core.ViewState.Error
import com.thomaskioko.stargazer.core.ViewState.Loading
import com.thomaskioko.stargazer.core.ViewState.Success
import com.thomaskioko.stargazer.testing.CoroutineScopeRule
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.anyBoolean

internal class GetReposViewModelTest {

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val scopeRule = CoroutineScopeRule()

    private val interactor: GetRepoListInteractor = mock()

    private lateinit var viewModel: GetReposViewModel

    @Before
    fun before() {
        viewModel = GetReposViewModel(
            interactor
        )
    }

    @Test
    fun `givenSuccessfulResponse verify successStateIsReturned`() = runBlocking {
        whenever(interactor(anyBoolean())) doReturn flowOf(Success(makeRepoViewDataModelList()))

        viewModel.repoList.test {

            viewModel.getRepoList(true)

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Success(makeRepoViewDataModelList()))
        }
    }

    @Test
    fun `givenFailureResponse verify errorStateIsReturned`() = runBlocking {
        val errorMessage = "Something went wrong"

        whenever(interactor(anyBoolean())) doReturn flowOf(Error(errorMessage))

        viewModel.repoList.test {

            viewModel.getRepoList(false)

            assertEquals(expectItem(), Loading<ViewState<List<RepoViewDataModel>>>())
            assertEquals(expectItem(), Error<ViewState<RepoViewDataModel>>(errorMessage))
        }
    }
}
