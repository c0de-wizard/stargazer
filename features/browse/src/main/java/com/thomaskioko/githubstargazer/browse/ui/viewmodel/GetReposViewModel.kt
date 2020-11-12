package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.*
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ScreenScope
class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor,
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var repoId: Long = 0

    suspend fun getRepos(): Flow<ViewState<List<RepoViewDataModel>>> {
        return interactor(connectivityAvailable)
    }

    suspend fun getRepoById(): Flow<ViewState<RepoViewDataModel>> {
        return getRepoByIdInteractor(repoId)
    }

    suspend fun updateBookmarkState(updateObject: UpdateObject): Flow<ViewState<RepoViewDataModel>> {
        return bookmarkStateInteractor(updateObject)
    }
}
