package com.thomaskioko.stargazer.repo_details.domain

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.thomaskioko.stargazer.core.ViewStateResult.Success
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.domain.model.UpdateObject
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoEntity
import com.thomaskioko.stargazer.repo_details.util.ViewMockData.makeRepoViewDataModel
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.mockito.ArgumentMatchers.anyLong

internal class UpdateRepoBookmarkStateInteractorTest {

    private val repository: GithubRepository = mock {
        on { getRepoByIdFlow(anyLong()) } doReturn flowOf(makeRepoEntity())
    }
    private val interactor = UpdateRepoBookmarkStateInteractor(repository)

    // @Test TODO Fix Flaky tests
    fun `whenever updateRepoIsInvoked expectedDataIsReturned`() = runBlocking {

        val result = interactor(UpdateObject(1, false)).toList()
        val expected = listOf(
            Success(makeRepoViewDataModel())
        )

        assertThat(result).isEqualTo(expected)
    }
}
