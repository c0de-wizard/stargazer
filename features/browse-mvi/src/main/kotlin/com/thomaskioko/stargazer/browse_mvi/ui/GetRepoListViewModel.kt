package com.thomaskioko.stargazer.browse_mvi.ui

import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.browse_mvi.interactor.GetReposInteractor
import com.thomaskioko.stargazer.browse_mvi.model.RepoViewDataModel
import com.thomaskioko.stargazer.browse_mvi.ui.ReposAction.LoadRepositories
import com.thomaskioko.stargazer.browse_mvi.ui.ReposAction.NavigateToRepoDetail
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class GetRepoListViewModel @AssistedInject constructor(
    private val interactor: GetReposInteractor,
    @Assisted private val screenNavigator: ScreenNavigator,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
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
                viewModelScope.launch(ioDispatcher) {
                    interactor(action.isConnected)
                        .onEach { channelState.offer(it.reduce()) }
                }
            }
            is NavigateToRepoDetail ->
                screenNavigator.goToScreen(RepoDetailScreen(action.repoId, action.extras))
        }
    }
}

internal fun ViewStateResult<List<RepoViewDataModel>>.reduce(): ReposViewState {
    return when (this) {
        is ViewStateResult.Loading -> ReposViewState.Loading
        is ViewStateResult.Success -> ReposViewState.ResultRepoList(data)
        is ViewStateResult.Error -> ReposViewState.Error(message)
    }
}
