package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.core.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor
) : ViewModel() {

    private val _repoListMutableStateFlow: MutableStateFlow<ViewState<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewState.loading())
    val repoList: SharedFlow<ViewState<List<RepoViewDataModel>>> get() = _repoListMutableStateFlow

    fun getRepoList(connectivityAvailable: Boolean) {
        interactor(connectivityAvailable)
            .onEach { _repoListMutableStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }
}
