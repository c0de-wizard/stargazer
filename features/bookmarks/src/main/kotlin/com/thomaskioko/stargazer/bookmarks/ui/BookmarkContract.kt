package com.thomaskioko.stargazer.bookmarks.ui

import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazers.common.model.RepoViewDataModel


sealed class BookmarkActions : ViewAction {
    object LoadRepositories : BookmarkActions()
    object NavigateToSettingsScreen : BookmarkActions()
    data class NavigateToRepoDetailScreen(val repoId: Long) : BookmarkActions()
}

internal sealed class BookmarkViewState : ViewState {
    object Loading : BookmarkViewState()
    data class Success(val list: List<RepoViewDataModel>) : BookmarkViewState()
    data class Error(val message: String = "") : BookmarkViewState()
}
