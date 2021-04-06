package com.thomaskioko.stargazer.trending.ui


import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazers.common.compose.model.RepoViewDataModel

sealed class ReposAction : ViewAction {
    object LoadRepositories : ReposAction()
    object NavigateToSettingsScreen : ReposAction()
    data class NavigateToRepoDetailScreen(val repoId: Long) : ReposAction()
}

internal sealed class ReposViewState : ViewState {
    object Loading : ReposViewState()
    data class Success(val list: List<RepoViewDataModel>) : ReposViewState()
    data class Error(val message: String = "") : ReposViewState()
}
