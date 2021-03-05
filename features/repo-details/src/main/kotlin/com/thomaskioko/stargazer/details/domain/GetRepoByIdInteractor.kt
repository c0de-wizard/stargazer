package com.thomaskioko.stargazer.details.domain

import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.model.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetRepoByIdInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Long, RepoViewDataModel>() {

    override fun run(params: Long): Flow<ViewStateResult<RepoViewDataModel>> = flow {

        emit(ViewStateResult.loading())

        val entity = repository.getRepoById(params)
        val repoViewDataModel = mapEntityToRepoViewModel(entity)

        emit(ViewStateResult.success(repoViewDataModel))
    }.catch {
        emit(ViewStateResult.error(it.message.orEmpty()))
    }
}
