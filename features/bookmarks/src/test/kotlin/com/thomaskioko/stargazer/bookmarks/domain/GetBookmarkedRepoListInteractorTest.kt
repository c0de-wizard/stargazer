package com.thomaskioko.stargazer.bookmarks.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
internal class GetBookmarkedRepoListInteractorTest {

    private val repository: GithubRepository = Mockito.mock(GithubRepository::class.java)
    private val interactor = GetBookmarkedRepoListInteractor(repository)

    @Test
    fun `whenever getBookmarkedRepos expectedDataIsReturned`() = runBlocking {
        whenever(repository.getBookmarkedRepos()).thenReturn(makeRepoEntityList())

        val result = interactor().toList()
        val expected = listOf(
            ViewStateResult.Loading(),
            ViewStateResult.Success(makeRepoViewDataModelList())
        )

        assertThat(expected).isEqualTo(result)
    }
}
