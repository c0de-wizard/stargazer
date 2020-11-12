package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.*
import com.thomaskioko.githubstargazer.bookmarks.data.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.interactor.invoke
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ScreenScope
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor
) : ViewModel() {

    suspend fun getBookmarkedRepos(): Flow<ViewState<List<RepoViewDataModel>>> {
        return interactor()
    }
}
