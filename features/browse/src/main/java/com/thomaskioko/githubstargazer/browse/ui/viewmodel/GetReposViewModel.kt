package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.domain.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.domain.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GetReposViewModel @ViewModelInject constructor(
    private val interactor: GetRepoListInteractor,
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor
) : ViewModel() {

    var connectivityAvailable: Boolean = false
    var repoId: Long = 0

    private val _mutableRepoListState: MutableSharedFlow<ViewState<List<RepoViewDataModel>>> =
        MutableSharedFlow()
    private val _mutableRepoState: MutableSharedFlow<ViewState<RepoViewDataModel>> =
        MutableSharedFlow()

    fun getRepos(): Flow<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch(Dispatchers.IO) {
            interactor(connectivityAvailable)
                .collect { _mutableRepoListState.emit(it) }
        }
        return _mutableRepoListState.asSharedFlow()
    }

    fun getRepoById(): Flow<ViewState<RepoViewDataModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            getRepoByIdInteractor(repoId)
                .collect { _mutableRepoState.emit(it) }
        }
        return _mutableRepoState.asSharedFlow()
    }

    fun updateBookmarkState(updateObject: UpdateObject): Flow<ViewState<RepoViewDataModel>> {
        viewModelScope.launch(Dispatchers.IO) {
            bookmarkStateInteractor(updateObject)
                .collect { _mutableRepoState.emit(it) }
        }
        return _mutableRepoState.asSharedFlow()
    }
}
