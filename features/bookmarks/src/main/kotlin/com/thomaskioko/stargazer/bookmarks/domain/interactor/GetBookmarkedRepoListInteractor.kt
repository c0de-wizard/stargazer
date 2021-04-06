package com.thomaskioko.stargazer.bookmarks.domain.interactor

import com.thomaskioko.stargazer.bookmarks.model.mapper.ViewDataMapper.mapEntityToRepoViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazers.common.compose.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBookmarkedRepoListInteractor @Inject constructor(
    private val repository: GithubRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Interactor<Unit, List<RepoViewDataModel>>() {

    override fun run(params: Unit): Flow<ViewStateResult<List<RepoViewDataModel>>> = flow {
        val result = repository.getBookmarkedRepos()
            .map { mapEntityToRepoViewModel(it) }

        emit(ViewStateResult.success(result))
    }
        .catch { emit(ViewStateResult.error(it.message.orEmpty())) }
        .flowOn(ioDispatcher)
}
