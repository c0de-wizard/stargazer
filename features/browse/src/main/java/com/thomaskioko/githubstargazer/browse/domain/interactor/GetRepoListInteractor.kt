package com.thomaskioko.githubstargazer.browse.domain.interactor

import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.Interactor
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import com.thomaskioko.stargazer.common_ui.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Boolean, List<RepoViewDataModel>>() {

    override suspend fun run(params: Boolean): Flow<ViewState<List<RepoViewDataModel>>> = flow {

        emit(ViewState.loading())

        val result = repository.getRepos(params)
            .map { mapEntityToRepoViewModel(it) }

        emit(ViewState.success(result))
    }.catch {
        emit(ViewState.error(it.message.orEmpty()))
    }
}
