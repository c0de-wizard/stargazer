package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.*
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor
) : ViewModel() {

    private var liveData: LiveData<ViewState<List<RepoViewDataModel>>> = MutableLiveData()
    var connectivityAvailable: Boolean = false

    fun getRepos(): LiveData<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch {
            liveData = interactor(connectivityAvailable)
                .asLiveData()
        }
        return liveData
    }
}