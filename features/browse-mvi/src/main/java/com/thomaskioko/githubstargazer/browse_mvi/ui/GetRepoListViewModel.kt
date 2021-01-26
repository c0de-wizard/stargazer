package com.thomaskioko.githubstargazer.browse_mvi.ui

import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse_mvi.interactor.GetReposInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GetRepoListViewModel @Inject constructor(
    private val interactor: GetReposInteractor
) : BaseViewModel<ReposIntent, ReposAction, ReposViewState>() {

    init {
        mState.postValue(ReposViewState.Loading)
    }

    override fun intentToAction(intent: ReposIntent): ReposAction {
        return when (intent) {
            is ReposIntent.DisplayData -> ReposAction.LoadRepositories(intent.isConnected)
            is ReposIntent.RepoItemClicked -> ReposAction.NavigateToRepoDetail(intent.repoId)
        }
    }

    override fun handleAction(action: ReposAction) {
        when (action) {
            is ReposAction.LoadRepositories -> {
                interactor(action.isConnected)
                    .onEach { mState.postValue(it.reduce()) }
                    .launchIn(viewModelScope)
            }
            is ReposAction.NavigateToRepoDetail -> {
                // TODO :: Navigate to repo details view
                Timber.d("Repo with ID: ${action.repoId} selected")
            }
        }
    }
}

fun ViewState<List<RepoViewDataModel>>.reduce(): ReposViewState {
    return when (this) {
        is ViewState.Loading -> ReposViewState.Loading
        is ViewState.Success -> ReposViewState.ResultRepoList(data)
        is ViewState.Error -> ReposViewState.Error(message)
    }
}
