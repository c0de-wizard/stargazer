package com.thomaskioko.stargazer.browse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.browse.domain.interactor.GetRepoListInteractor
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GetReposViewModel @Inject constructor(
    private val interactor: GetRepoListInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _repoListMutableStateResultFlow: MutableStateFlow<ViewStateResult<List<RepoViewDataModel>>> =
        MutableStateFlow(ViewStateResult.loading())
    val repoList: SharedFlow<ViewStateResult<List<RepoViewDataModel>>> get() = _repoListMutableStateResultFlow

    fun getRepoList(connectivityAvailable: Boolean) {
        viewModelScope.launch(ioDispatcher) {
            interactor(connectivityAvailable)
                .onEach { _repoListMutableStateResultFlow.value = it }
        }
    }
}
