package com.thomaskioko.githubstargazer.repo_details.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repo_details.domain.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.repo_details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.repo_details.domain.model.UpdateObject
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RepoDetailsViewModel @ViewModelInject constructor(
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor
) : ViewModel() {

    val repoMutableStateFlow: MutableStateFlow<ViewState<RepoViewDataModel>> =
        MutableStateFlow(ViewState.loading())

    val repoUpdateMutableStateFlow: MutableStateFlow<ViewState<RepoViewDataModel>> =
        MutableStateFlow(ViewState.loading())

    fun getRepoById(repoId: Long) {
        getRepoByIdInteractor(repoId)
            .onEach { repoMutableStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }

    fun updateBookmarkState(updateObject: UpdateObject) {
        bookmarkStateInteractor(updateObject)
            .onEach { repoUpdateMutableStateFlow.emit(it) }
            .launchIn(viewModelScope)
    }
}
