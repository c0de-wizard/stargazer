package com.thomaskioko.stargazer.browse.domain.interactor

import androidx.paging.PagingData
import androidx.paging.map
import com.thomaskioko.stargazer.browse.model.ViewDataMapper.mapEntityToViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.interactor.Interactor
import com.thomaskioko.stargazer.repository.GithubRepository
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class SearchRepositoriesInteractor @Inject constructor(
    private val repository: GithubRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Interactor<String, PagingData<RepoViewDataModel>>() {

    override fun run(params: String): Flow<ViewStateResult<PagingData<RepoViewDataModel>>> =
        repository.searchRepository(params)
            .map { data -> data.map { mapEntityToViewModel(it) } }
            .map { pagedViewModelDataList -> ViewStateResult.success(pagedViewModelDataList) }
            .catch { emit(ViewStateResult.error(it)) }
            .flowOn(ioDispatcher)
}
