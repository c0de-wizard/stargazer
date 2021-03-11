package com.thomaskioko.stargazer.repo_details.domain

import app.cash.turbine.test
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.domain.model.UpdateObject
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoEntity
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong

internal class UpdateRepoBookmarkStateInteractorTest {

    private val repository: GithubRepository = mock {
        onBlocking { getRepoById(anyLong()) } doReturn makeRepoEntity()
    }
    private val testDispatcher = TestCoroutineDispatcher()
    private val interactor = UpdateRepoBookmarkStateInteractor(repository, testDispatcher)

    @Test
    fun `whenever updateRepoIsInvoked expectedDataIsReturned`() = runBlocking {
        interactor(UpdateObject(1, false)).test {
            assertEquals(expectItem(), Success(makeRepoViewDataModel()))
            expectComplete()
        }
    }
}
