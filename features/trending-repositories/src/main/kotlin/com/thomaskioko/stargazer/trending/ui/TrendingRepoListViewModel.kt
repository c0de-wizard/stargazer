package com.thomaskioko.stargazer.trending.ui

import androidx.paging.PagingData
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.ScreenDirections
import com.thomaskioko.stargazer.navigation.ScreenNavigationManager
import com.thomaskioko.stargazer.trending.interactor.GetTrendingReposInteractor
import com.thomaskioko.stargazer.trending.ui.ReposAction.LoadRepositories
import com.thomaskioko.stargazer.trending.ui.ReposAction.NavigateToSettingsScreen
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class TrendingRepoListViewModel @Inject constructor(
    private val interactorTrending: GetTrendingReposInteractor,
    private val screenNavigationManager: ScreenNavigationManager,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<ReposAction, ReposViewState>(
    initialViewState = ReposViewState.Loading,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: ReposAction) {
        when (action) {
            is LoadRepositories -> {
                interactorTrending()
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .stateIn(ioScope, SharingStarted.Eagerly, emptyList<RepoViewDataModel>())
            }
            is NavigateToSettingsScreen -> {
                screenNavigationManager.navigate(ScreenDirections.Settings)
            }
            is ReposAction.NavigateToRepoDetailScreen -> {
                screenNavigationManager.navigate(ScreenDirections.Details)
            }
        }
    }
}

internal fun ViewStateResult<PagingData<RepoViewDataModel>>.reduce(): ReposViewState {
    return when (this) {
        is ViewStateResult.Loading -> ReposViewState.Loading
        is ViewStateResult.Success -> ReposViewState.Success(data)
        is ViewStateResult.Error -> ReposViewState.Error(message)
    }
}
