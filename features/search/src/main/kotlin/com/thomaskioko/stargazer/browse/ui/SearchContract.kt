package com.thomaskioko.stargazer.browse.ui

import androidx.paging.PagingData
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazers.common.model.RepoViewDataModel

sealed class SearchAction : ViewAction {
    object BackPressed : SearchAction()
    data class SearchRepository(val query: String) : SearchAction()
    data class NavigateToRepoDetailScreen(val repoId: Long) : SearchAction()
}

internal sealed class SearchViewState : ViewState {
    object Init : SearchViewState()
    object Loading : SearchViewState()
    data class Success(val list: PagingData<RepoViewDataModel>) : SearchViewState()
    data class Error(val message: String = "") : SearchViewState()
}
