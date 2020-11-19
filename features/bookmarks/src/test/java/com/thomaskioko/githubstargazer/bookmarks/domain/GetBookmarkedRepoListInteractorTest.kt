package com.thomaskioko.githubstargazer.bookmarks.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.bookmarks.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        val result = interactor().toList()
        val expected = listOf(
            ViewState.Loading(),
            ViewState.Success(makeRepoViewDataModelList())
        )

        assertThat(expected).isEqualTo(result)
    }
}
