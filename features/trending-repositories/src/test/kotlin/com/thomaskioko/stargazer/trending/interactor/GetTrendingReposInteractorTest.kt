package com.thomaskioko.stargazer.trending.interactor

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoEntityList
import com.thomaskioko.stargazer.trending.interactor.ViewMockData.makeRepoViewDataModelList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyBoolean

internal class GetTrendingReposInteractorTest {

    private val repository: GithubRepository = mock {
        on { getTrendingTrendingRepositories(anyBoolean()) } doReturn flowOf(makeRepoEntityList())
    }
    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val interactor = GetTrendingReposInteractor(repository, testCoroutineDispatcher)

    @Test
    fun `whenever getReposIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor(true).test {
            assertEquals(expectItem(), ViewStateResult.Success(makeRepoViewDataModelList()))
            expectComplete()
        }
    }
}
