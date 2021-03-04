package com.thomaskioko.stargazer.browse_mvi.ui

import androidx.navigation.Navigator
import com.thomaskioko.stargazer.browse_mvi.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewIntent
import com.thomaskioko.stargazer.core.presentation.ViewState

sealed class ReposIntent : ViewIntent {
    data class DisplayData(val isConnected: Boolean) : ReposIntent()
    data class RepoItemClicked(val repoId: Long, val extras: Navigator.Extras) : ReposIntent()
}

sealed class ReposAction : ViewAction {
    data class LoadRepositories(val isConnected: Boolean) : ReposAction()
    data class NavigateToRepoDetail(val repoId: Long, val extras: Navigator.Extras) : ReposAction()
}

internal sealed class ReposViewState : ViewState {
    object Loading : ReposViewState()
    data class ResultRepoList(val list: List<RepoViewDataModel>) : ReposViewState()
    data class Error(val message: String = "") : ReposViewState()
}
