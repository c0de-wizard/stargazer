package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor,
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var repoId: Long = 0

    private val _mutableRepoListState: MutableStateFlow<ViewState<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewState.loading())
    private val _mutableRepoState: MutableStateFlow<ViewState<RepoViewDataModel>> =
        MutableStateFlow(ViewState.loading())

    fun getRepos(): Flow<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch(Dispatchers.IO) {
            interactor(connectivityAvailable)
                .collect { _mutableRepoListState.value = it }
        }
        return _mutableRepoListState
    }

    fun getRepoById(): Flow<ViewState<RepoViewDataModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoByIdInteractor(repoId)
                .collect { _mutableRepoState.value = it }
        }
        return _mutableRepoState
    }

    fun updateBookmarkState(updateObject: UpdateObject): Flow<ViewState<RepoViewDataModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkStateInteractor(updateObject)
                .collect { _mutableRepoState.value = it }
        }
        return _mutableRepoState
    }
}
