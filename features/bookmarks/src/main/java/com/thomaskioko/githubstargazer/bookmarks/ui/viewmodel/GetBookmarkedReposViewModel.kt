package com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel

import androidx.lifecycle.*
import com.thomaskioko.githubstargazer.bookmarks.data.interactor.GetBookmarkedRepoListInteractor
import com.thomaskioko.githubstargazer.bookmarks.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import com.thomaskioko.githubstargazer.core.interactor.invoke
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class GetBookmarkedReposViewModel @Inject constructor(
    private val interactor: GetBookmarkedRepoListInteractor
) : ViewModel() {

    private var liveData: LiveData<ViewState<List<RepoViewDataModel>>> = MutableLiveData()

    fun getBookmarkedRepos(): LiveData<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch {
            liveData = interactor()
                .asLiveData()
        }
        return liveData
    }

}