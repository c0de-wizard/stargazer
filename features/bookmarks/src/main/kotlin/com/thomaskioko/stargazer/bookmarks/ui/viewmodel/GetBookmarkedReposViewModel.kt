package com.thomaskioko.stargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.thomaskioko.stargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.stargazer.bookmarks.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.interactor.invoke
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _mutableRepoListStateResult: MutableStateFlow<ViewStateResult<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewStateResult.loading())
    val bookmarkedList: SharedFlow<ViewStateResult<List<RepoViewDataModel>>> get() = _mutableRepoListStateResult

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(ioDispatcher + viewModelJob)

    fun getBookmarkedRepos() {
        interactor()
            .onEach { _mutableRepoListStateResult.emit(it) }
            .launchIn(ioScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
