package com.thomaskioko.stargazer.browse.ui

import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState

sealed class SearchAction : ViewAction {
    data class SearchRepository(val query: String) : SearchAction()
}

internal sealed class SearchViewState : ViewState {
    object Init : SearchViewState()
    object Loading : SearchViewState()
    data class Success(val list: List<RepoViewDataModel>) : SearchViewState()
    data class Error(val message: String = "") : SearchViewState()
}