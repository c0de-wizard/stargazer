package com.thomaskioko.githubstargazer.bookmarks.data.interactor

import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.Interactor
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import com.thomaskioko.stargazer.common_ui.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookmarkedRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Unit, List<RepoViewDataModel>>() {

    override suspend fun run(params: Unit): Flow<ViewState<List<RepoViewDataModel>>> = flow {

        emit(ViewState.loading())

        val result = repository.getBookmarkedRepos()
            .map { mapEntityToRepoViewModel(it) }

        emit(ViewState.success(result))
    }.catch {
        emit(ViewState.error(it.message.orEmpty()))
    }
}
