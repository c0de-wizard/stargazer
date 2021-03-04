package com.thomaskioko.stargazer.repo_details.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoEntity
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        val result = interactor(anyLong()).toList()
        val expected = listOf(
            ViewStateResult.Loading(),
            ViewStateResult.Success(makeRepoViewDataModel())
        )

        assertThat(result).isEqualTo(expected)
    }
}
