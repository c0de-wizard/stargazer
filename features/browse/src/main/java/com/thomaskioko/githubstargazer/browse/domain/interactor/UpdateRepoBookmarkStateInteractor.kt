package com.thomaskioko.githubstargazer.browse.domain.interactor

import com.thomaskioko.githubstargazer.browse.domain.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.Interactor
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import com.thomaskioko.stargazer.common_ui.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepoBookmarkStateInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<UpdateObject, RepoViewDataModel>() {

    override fun run(params: UpdateObject): Flow<ViewState<RepoViewDataModel>> = flow {

        emit(ViewState.loading())

        val isBookmarked = if (params.isBookmarked) 1 else 0

        repository.updateRepoBookMarkStatus(isBookmarked, params.repoId)

        val entity = repository.getRepoById(params.repoId)
        val repoViewDataModel = mapEntityToRepoViewModel(entity)

        emit(ViewState.success(repoViewDataModel))
    }.catch {
        emit(ViewState.error(it.message.orEmpty()))
    }
}
