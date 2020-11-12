package com.thomaskioko.githubstargazer.browse.ui.viewmodel

import androidx.lifecycle.*
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoByIdInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.GetRepoListInteractor
import com.thomaskioko.githubstargazer.browse.data.interactor.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.githubstargazer.browse.data.model.RepoViewDataModel
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.injection.scope.ScreenScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ScreenScope
class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor,
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor
) : ViewModel() {

    private var liveData: LiveData<ViewState<List<RepoViewDataModel>>> = MutableLiveData()
    private var repoIdLiveData: LiveData<ViewState<RepoViewDataModel>> = MutableLiveData()
    var connectivityAvailable: Boolean = false
    var repoId: Long = 0

    fun getRepos(): LiveData<ViewState<List<RepoViewDataModel>>> {
        viewModelScope.launch {
            liveData = interactor(connectivityAvailable)
                .asLiveData()
        }
        return liveData
    }

    fun getRepoById(): LiveData<ViewState<RepoViewDataModel>> {
        viewModelScope.launch {
            repoIdLiveData = getRepoByIdInteractor(repoId)
                .asLiveData()
        }
        return repoIdLiveData
    }

    suspend fun updateBookmarkState(updateObject: UpdateObject): Flow<ViewState<RepoViewDataModel>> {
        return bookmarkStateInteractor(updateObject)
    }
}