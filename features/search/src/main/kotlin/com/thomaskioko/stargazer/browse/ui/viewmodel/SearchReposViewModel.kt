package com.thomaskioko.stargazer.browse.ui.viewmodel

import androidx.paging.PagingData
import com.thomaskioko.stargazer.browse.domain.interactor.SearchRepositoriesInteractor
import com.thomaskioko.stargazer.browse.ui.SearchAction
import com.thomaskioko.stargazer.browse.ui.SearchAction.BackPressed
import com.thomaskioko.stargazer.browse.ui.SearchAction.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.browse.ui.SearchAction.SearchRepository
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailsScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

internal class SearchReposViewModel @AssistedInject constructor(
    private val interactor: SearchRepositoriesInteractor,
    @Assisted private val screenNavigator: ScreenNavigator,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<SearchAction, SearchViewState>(
    initialViewState = SearchViewState.Init,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: SearchAction) {
        when (action) {
            BackPressed -> screenNavigator.goBack()
            is SearchRepository ->
                interactor(action.query)
                    .debounce(250)
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .stateIn(ioScope, SharingStarted.Eagerly, emptyList<RepoViewDataModel>())
            is NavigateToRepoDetailScreen -> screenNavigator.goToScreen(
                RepoDetailsScreen(action.repoId)
            )
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ScreenNavigator> {
        override fun create(data: ScreenNavigator): SearchReposViewModel
    }

}

internal fun ViewStateResult<PagingData<RepoViewDataModel>>.reduce(): SearchViewState {
    return when (this) {
        is ViewStateResult.Loading -> SearchViewState.Loading
        is ViewStateResult.Success -> SearchViewState.Success(data)
        is ViewStateResult.Error -> SearchViewState.Error(message)
    }
}
