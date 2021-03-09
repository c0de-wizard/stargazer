package com.thomaskioko.stargazer.repo_details.domain

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoEntity
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong

internal class GetRepoByIdInteractorTest {

    private val repository: GithubRepository = mock {
        onBlocking { getRepoById(anyLong()) } doReturn makeRepoEntity()
    }
    private val testDispatcher = TestCoroutineDispatcher()
    private val interactor = GetRepoByIdInteractor(repository, testDispatcher)

    @Test
    fun `whenever getRepoByIdIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor(1).test {
            assertEquals(ViewStateResult.Success(makeRepoViewDataModel()), expectItem())
            expectComplete()
        }
    }
}
