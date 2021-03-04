package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.interactor.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor
) : ViewModel() {

    private val _mutableRepoListStateResult: MutableSharedFlow<ViewStateResult<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewStateResult.loading())
    val bookmarkedList: SharedFlow<ViewStateResult<List<RepoViewDataModel>>> get() = _mutableRepoListStateResult

    fun getBookmarkedRepos() {
        interactor()
            .onEach { _mutableRepoListStateResult.emit(it) }
            .launchIn(viewModelScope)
    }
}
