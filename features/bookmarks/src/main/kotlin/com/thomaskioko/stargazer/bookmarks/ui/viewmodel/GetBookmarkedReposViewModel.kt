package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.interactor.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _mutableRepoListStateResult: MutableStateFlow<ViewStateResult<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewStateResult.loading())
    val bookmarkedList: SharedFlow<ViewStateResult<List<RepoViewDataModel>>> get() = _mutableRepoListStateResult

    fun getBookmarkedRepos() {
        viewModelScope.launch(ioDispatcher) {
            interactor()
                .onStart { _mutableRepoListStateResult.value = ViewStateResult.loading() }
                .onEach { _mutableRepoListStateResult.value = it }
        }
    }
}
