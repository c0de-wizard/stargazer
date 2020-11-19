package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.githubstargazer.bookmarks.domain.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor
) : ViewModel() {

    private val _mutableRepoListState: MutableSharedFlow<ViewState<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewState.loading())

    fun getBookmarkedRepos(): Flow<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch(Dispatchers.IO) {
            interactor()
                .collect { _mutableRepoListState.emit(it) }
        }
        return _mutableRepoListState
    }
}
