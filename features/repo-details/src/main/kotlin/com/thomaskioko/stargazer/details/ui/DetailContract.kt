package com.thomaskioko.stargazer.details.ui

import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazer.details.domain.model.UpdateObject
import com.thomaskioko.stargazer.details.model.RepoViewDataModel

sealed class DetailAction : ViewAction {
    object BackPressed: DetailAction()
    data class LoadRepo(val repoId: Long) : DetailAction()
    data class UpdateRepo(val data: UpdateObject) : DetailAction()
}

internal sealed class DetailViewState : ViewState {
    object Loading : DetailViewState()
    data class Success(val viewDataModel: RepoViewDataModel) : DetailViewState()
    data class Error(val message: String = "") : DetailViewState()
}