package com.thomaskioko.githubstargazer.browse.data.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoEntity
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong

@ExperimentalCoroutinesApi
internal class GetRepoByIdInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetRepoByIdInteractor(repository)

    @Test
    fun `whenever getRepoByIdIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepoById(anyLong())).doReturn(makeRepoEntity())

        val expected = interactor(anyLong()).toList()
        val result = listOf(
            ViewState.Loading(),
            ViewState.Success(makeRepoViewDataModel())
        )

        interactor(anyLong())
            .collect {
                assertThat(expected).isEqualTo(result)
            }
    }
}