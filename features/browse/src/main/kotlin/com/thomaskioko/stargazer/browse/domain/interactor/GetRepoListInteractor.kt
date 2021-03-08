package com.thomaskioko.stargazer.browse.domain.interactor

import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.browse.model.ViewDataMapper.mapEntityListToRepoViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Boolean, List<RepoViewDataModel>>() {

    override fun run(params: Boolean): Flow<ViewStateResult<List<RepoViewDataModel>>> =
        repository.getRepositoryList(params)
            .map { ViewStateResult.success(mapEntityListToRepoViewModel(it)) }
            .catch { emit(ViewStateResult.error(it.message.orEmpty())) }
}
