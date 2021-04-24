package com.thomaskioko.stargazer.browse.domain.interactor

import com.thomaskioko.stargazer.browse.model.ViewDataMapper.mapEntityListToRepoViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchRepositoriesInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<String, List<RepoViewDataModel>>() {

    override fun run(params: String): Flow<ViewStateResult<List<RepoViewDataModel>>> =
        repository.searchRepository(params)
            .map { ViewStateResult.success(mapEntityListToRepoViewModel(it)) }
            .catch { emit(ViewStateResult.error(it.message.orEmpty())) }
}
