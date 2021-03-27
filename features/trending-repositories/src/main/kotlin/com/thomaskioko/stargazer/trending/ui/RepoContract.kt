package com.thomaskioko.stargazer.trending.ui

import androidx.navigation.Navigator
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazer.trending.model.RepoViewDataModel

sealed class ReposAction : ViewAction {
    object LoadRepositories : ReposAction()
    object NavigateToSettingsScreen : ReposAction()
    //TODO:: Get rid of the extras object when we fully navigate to compose
    data class NavigateToRepoDetail(val repoId: Long, val extras: Navigator.Extras) : ReposAction()
    data class NavigateToRepoDetailScreen(val repoId: Long) : ReposAction()
}

internal sealed class ReposViewState : ViewState {
    object Loading : ReposViewState()
    data class Success(val list: List<RepoViewDataModel>) : ReposViewState()
    data class Error(val message: String = "") : ReposViewState()
}
