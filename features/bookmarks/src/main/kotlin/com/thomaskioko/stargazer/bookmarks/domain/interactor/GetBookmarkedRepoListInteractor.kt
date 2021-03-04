package com.thomaskioko.stargazer.bookmarks.domain.interactor

import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.bookmarks.model.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.api.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetBookmarkedRepoListInteractor @Inject constructor(
    private val repository: GithubRepository
) : Interactor<Unit, List<RepoViewDataModel>>() {

    override fun run(params: Unit): Flow<ViewStateResult<List<RepoViewDataModel>>> = flow {

        emit(ViewStateResult.loading())

        val result = repository.getBookmarkedRepos()
            .map { mapEntityToRepoViewModel(it) }

        emit(ViewStateResult.success(result))
    }.catch {
        emit(ViewStateResult.error(it.message.orEmpty()))
    }
}
