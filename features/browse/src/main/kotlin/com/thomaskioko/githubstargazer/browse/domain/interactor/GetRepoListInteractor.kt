package com.thomaskioko.githubstargazer.browse.domain.interactor

import com.thomaskioko.githubstargazer.browse.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.browse.model.ViewDataMapper.mapEntityListToRepoViewModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.Interactor
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Boolean, List<RepoViewDataModel>>() {

    override fun run(params: Boolean): Flow<ViewState<List<RepoViewDataModel>>> =
        repository.getRepositoryList(params)
            .map { ViewState.success(mapEntityListToRepoViewModel(it)) }
            .catch { emit(ViewState.error(it.message.orEmpty())) }
}