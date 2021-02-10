package com.thomaskioko.githubstargazer.browse_mvi.ui

import com.thomaskioko.githubstargazer.browse_mvi.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.core.presentation.ViewAction
import com.thomaskioko.githubstargazer.core.presentation.ViewIntent
import com.thomaskioko.githubstargazer.core.presentation.ViewState

sealed class ReposIntent : ViewIntent {
    data class DisplayData(val isConnected: Boolean) : ReposIntent()
    data class RepoItemClicked(val repoId: Long, val extras: String) : ReposIntent()
}

sealed class ReposAction : ViewAction {
    data class LoadRepositories(val isConnected: Boolean) : ReposAction()
    data class NavigateToRepoDetail(val repoId: Long, val extras: String) : ReposAction()
}

internal sealed class ReposViewState : ViewState {
    object Loading : ReposViewState()
    data class ResultRepoList(val list: List<RepoViewDataModel>) : ReposViewState()
    data class Error(val message: String = "") : ReposViewState()
}
