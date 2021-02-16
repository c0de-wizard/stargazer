package com.thomaskioko.githubstargazer.browse_mvi.ui

import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.browse_mvi.interactor.GetReposInteractor
import com.thomaskioko.githubstargazer.browse_mvi.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.browse_mvi.ui.ReposAction.LoadRepositories
import com.thomaskioko.githubstargazer.browse_mvi.ui.ReposAction.NavigateToRepoDetail
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.githubstargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class GetRepoListViewModel @AssistedInject constructor(
    private val interactor: GetReposInteractor,
    @Assisted private val screenNavigator: ScreenNavigator
) : BaseViewModel<ReposIntent, ReposAction, ReposViewState>(
    initialViewState = ReposViewState.Loading
) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ScreenNavigator> {
        override fun create(data: ScreenNavigator): GetRepoListViewModel
    }

    override fun intentToAction(intent: ReposIntent): ReposAction {
        return when (intent) {
            is ReposIntent.DisplayData -> LoadRepositories(intent.isConnected)
            is ReposIntent.RepoItemClicked -> NavigateToRepoDetail(intent.repoId, intent.extras)
        }
    }

    override fun handleAction(action: ReposAction) {
        when (action) {
            is LoadRepositories -> {
                interactor(action.isConnected)
                    .onEach { channelState.offer(it.reduce()) }
                    .launchIn(viewModelScope)
            }
            is NavigateToRepoDetail ->
                screenNavigator.goToScreen(RepoDetailScreen(action.repoId, action.extras))
        }
    }
}

internal fun ViewState<List<RepoViewDataModel>>.reduce(): ReposViewState {
    return when (this) {
        is ViewState.Loading -> ReposViewState.Loading
        is ViewState.Success -> ReposViewState.ResultRepoList(data)
        is ViewState.Error -> ReposViewState.Error(message)
    }
}
