package com.thomaskioko.stargazer.browse.domain.interactor

import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.browse.model.ViewDataMapper.mapEntityListToRepoViewModel
import com.thomaskioko.stargazer.core.ViewState
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.api.GithubRepository
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
