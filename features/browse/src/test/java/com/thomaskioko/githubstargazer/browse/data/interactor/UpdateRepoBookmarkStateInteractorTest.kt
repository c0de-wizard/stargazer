package com.thomaskioko.githubstargazer.browse.data.interactor

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoEntityList
import com.thomaskioko.githubstargazer.browse.data.ViewMockData.makeRepoViewDataModelList
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyLong

@ExperimentalCoroutinesApi
internal class UpdateRepoBookmarkStateInteractorTest {

    private val repository: GithubRepository = mock()
    private val interactor = UpdateRepoBookmarkStateInteractor(repository)

    @Test
    fun `whenever updateRepoIsInvoked expectedDataIsReturned`() = runBlocking {
        whenever(repository.getRepoById(anyLong())).doReturn(makeRepoEntityList()[0])

        val updateObject = UpdateObject(anyLong(), anyBoolean())
        val expected = interactor(updateObject).toList()
        val result = listOf(
            ViewState.Loading(),
            ViewState.Success(makeRepoViewDataModelList()[0])
        )

        interactor(updateObject)
            .collect {
                assertThat(expected).isEqualTo(result)
            }
    }
}
