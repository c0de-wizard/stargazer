package com.thomaskioko.stargazer.details.domain

import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.model.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.repository.GithubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UpdateRepoBookmarkStateInteractor @Inject constructor(
    private val repository: GithubRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Interactor<RepoViewDataModel, RepoViewDataModel>() {

    override fun run(params: RepoViewDataModel): Flow<ViewStateResult<RepoViewDataModel>> =
        flowOf(params.isBookmarked)
            .map { isBookmarked -> if (isBookmarked) 1 else 0 }
            .map { repository.updateRepoBookMarkStatus(it, params.repoId) }
            .map { repository.getRepoById(params.repoId) }
            .map { ViewStateResult.success(mapEntityToRepoViewModel(it)) }
            .catch { emit(ViewStateResult.error(it.message.orEmpty())) }
            .flowOn(ioDispatcher)
}
