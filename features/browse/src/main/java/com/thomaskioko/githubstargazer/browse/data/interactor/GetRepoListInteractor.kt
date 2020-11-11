package com.thomaskioko.githubstargazer.browse.data.interactor

import com.thomaskioko.githubstargazer.browse.data.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.core.injection.ViewState
import com.thomaskioko.githubstargazer.core.injection.interactor.Interactor
import com.thomaskioko.githubstargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Boolean, List<RepoViewDataModel>>(){

    override suspend fun run(params: Boolean): Flow<ViewState<List<RepoViewDataModel>>> = flow {

        emit(ViewState.loading())

        val result = repository.getTopRepos(params)
            .map { mapEntityToRepoViewModel(it) }

        emit(ViewState.success(result))
    }.catch {
        emit(ViewState.error(it.message.orEmpty()))
    }
}