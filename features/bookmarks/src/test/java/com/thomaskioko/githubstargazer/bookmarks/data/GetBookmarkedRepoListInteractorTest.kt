package com.thomaskioko.githubstargazer.bookmarks.data

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.bookmarks.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.bookmarks.data.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

@ExperimentalCoroutinesApi
internal class GetBookmarkedRepoListInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = GetBookmarkedRepoListInteractor(repository)

    @Test
    fun `whenever getBookmarkedRepos expectedDataIsReturned`() = runBlocking {
        whenever(repository.getBookmarkedRepos()).doReturn(makeRepoEntityList())

        val expected = interactor().toList()
        val result = listOf(
            ViewState.Loading(),
            ViewState.Success(makeRepoViewDataModelList())
        )

        interactor()
            .collect {
                assertThat(expected).isEqualTo(result)
            }
    }
}
