package com.thomaskioko.stargazer.trending.ui

import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailsScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.trending.interactor.GetTrendingReposInteractor
import com.thomaskioko.stargazer.trending.model.RepoViewDataModel
import com.thomaskioko.stargazer.trending.ui.ReposAction.LoadRepositories
import com.thomaskioko.stargazer.trending.ui.ReposAction.NavigateToSettingsScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class GetRepoListViewModel @AssistedInject constructor(
    private val interactorTrending: GetTrendingReposInteractor,
    @Assisted private val screenNavigator: ScreenNavigator,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<ReposAction, ReposViewState>(
    initialViewState = ReposViewState.Loading,
    dispatcher = ioDispatcher
) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ScreenNavigator> {
        override fun create(data: ScreenNavigator): GetRepoListViewModel
    }

    override fun handleAction(action: ReposAction) {
        when (action) {
            is LoadRepositories -> {
                interactorTrending()
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .launchIn(ioScope)
            }
            is NavigateToSettingsScreen -> screenNavigator.goToScreen(NavigationScreen.SettingsScreen)
            is ReposAction.NavigateToRepoDetailScreen -> screenNavigator.goToScreen(
                RepoDetailsScreen(action.repoId)
            )
        }
    }
}

internal fun ViewStateResult<List<RepoViewDataModel>>.reduce(): ReposViewState {
    return when (this) {
        is ViewStateResult.Loading -> ReposViewState.Loading
        is ViewStateResult.Success -> ReposViewState.Success(data)
        is ViewStateResult.Error -> ReposViewState.Error(message)
    }
}
