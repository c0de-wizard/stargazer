package com.thomaskioko.stargazer.repo_details.domain

import com.thomaskioko.stargazer.core.ViewState
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repo_details.domain.model.UpdateObject
import com.thomaskioko.stargazer.repo_details.model.RepoViewDataModel
import com.thomaskioko.stargazer.repo_details.model.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

internal class UpdateRepoBookmarkStateInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<UpdateObject, RepoViewDataModel>() {

    override fun run(params: UpdateObject): Flow<ViewState<RepoViewDataModel>> =
        flowOf(params.isBookmarked)
            .map { isBookmarked ->
                if (isBookmarked) 1 else 0
            }
            .map { repository.updateRepoBookMarkStatus(it, params.repoId) }
            .flatMapConcat { repository.getRepoByIdFlow(params.repoId) }
            .map { ViewState.success(mapEntityToRepoViewModel(it)) }
            .catch { emit(ViewState.error(it.message.orEmpty())) }

}
