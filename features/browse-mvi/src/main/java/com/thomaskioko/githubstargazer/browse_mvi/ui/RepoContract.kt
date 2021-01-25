package com.thomaskioko.githubstargazer.browse_mvi.ui

import com.thomaskioko.githubstargazer.core.viewmodel.ViewIntent
import com.thomaskioko.githubstargazer.core.viewmodel.ViewState
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel

sealed class ReposIntent : ViewIntent {
    data class LoadRepositories(val isConnected: Boolean) : ReposIntent()
    data class RepositorySelected(val repoId: Long) : ReposIntent()
}

sealed class ReposViewState : ViewState {
    object Loading : ReposViewState()
    data class ResultRepoList(val list: List<RepoViewDataModel>) : ReposViewState()
    data class Error(val message: String = "") : ReposViewState()
}
