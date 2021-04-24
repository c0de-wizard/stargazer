package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import com.thomaskioko.stargazer.bookmarks.domain.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.LoadRepositories
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToSettingsScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkViewState
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.interactor.invoke
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailsScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SettingsScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

internal class GetBookmarkedReposViewModel @AssistedInject constructor(
    private val interactor: GetBookmarkedRepoListInteractor,
    @Assisted private val screenNavigator: ScreenNavigator,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) :  BaseViewModel<BookmarkActions, BookmarkViewState>(
    initialViewState = BookmarkViewState.Loading,
    dispatcher = ioDispatcher
){

    override fun handleAction(action: BookmarkActions) {
       when(action){
           LoadRepositories -> {
               interactor()
                   .onEach { mutableViewState.emit(it.reduce()) }
                   .stateIn(ioScope, SharingStarted.Eagerly, emptyList<RepoViewDataModel>())
           }
           NavigateToSettingsScreen -> screenNavigator.goToScreen(SettingsScreen)
           is NavigateToRepoDetailScreen -> screenNavigator.goToScreen(
               RepoDetailsScreen(action.repoId)
           )
       }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ScreenNavigator> {
        override fun create(data: ScreenNavigator): GetBookmarkedReposViewModel
    }
}

internal fun ViewStateResult<List<RepoViewDataModel>>.reduce(): BookmarkViewState {
    return when (this) {
        is ViewStateResult.Loading -> BookmarkViewState.Loading
        is ViewStateResult.Success -> BookmarkViewState.Success(data)
        is ViewStateResult.Error -> BookmarkViewState.Error(message)
    }
}
