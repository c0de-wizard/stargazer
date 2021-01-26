package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class GetBookmarkedReposViewModel @Inject constructor(
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
