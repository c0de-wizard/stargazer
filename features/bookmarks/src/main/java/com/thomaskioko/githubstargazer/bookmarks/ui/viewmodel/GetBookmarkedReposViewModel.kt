package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.*

class GetBookmarkedReposViewModel @ViewModelInject constructor(
    private val interactor: GetBookmarkedRepoListInteractor
) : ViewModel() {

    private val _mutableRepoListState: MutableSharedFlow<ViewState<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewState.loading())
    val bookmarkedList: SharedFlow<ViewState<List<RepoViewDataModel>>> get() = _mutableRepoListState

    fun getBookmarkedRepos() {
        interactor()
            .onEach { _mutableRepoListState.emit(it) }
            .launchIn(viewModelScope)
    }
}
