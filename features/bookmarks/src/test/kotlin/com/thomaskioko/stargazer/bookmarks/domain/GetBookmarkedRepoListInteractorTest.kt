package com.thomaskioko.stargazer.bookmarks.domain

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.bookmarks.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class GetBookmarkedRepoListInteractorTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val repository: GithubRepository = mock {
        onBlocking { getBookmarkedRepos() } doReturn makeRepoEntityList()
    }

    private val interactor = GetBookmarkedRepoListInteractor(repository, testDispatcher)

    @Test
    fun `whenever getBookmarkedRepos expectedDataIsReturned`() = runBlocking {
        interactor().test {
            assertEquals(expectItem(), ViewStateResult.Success(makeRepoViewDataModelList()))
            expectComplete()
        }
    }
}
