package com.thomaskioko.stargazer.details.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.domain.model.UpdateObject
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RepoDetailsViewModel @Inject constructor(
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(ioDispatcher + viewModelJob)

    val repoMutableStateResultFlow: MutableStateFlow<ViewStateResult<RepoViewDataModel>> =
        MutableStateFlow(ViewStateResult.loading())

    val repoUpdateMutableStateResultFlow: MutableStateFlow<ViewStateResult<RepoViewDataModel>> =
        MutableStateFlow(ViewStateResult.loading())

    fun getRepoById(repoId: Long) {
        getRepoByIdInteractor(repoId)
            .onEach { repoMutableStateResultFlow.emit(it) }
            .launchIn(ioScope)
    }

    fun updateBookmarkState(updateObject: UpdateObject) {
        bookmarkStateInteractor(updateObject)
            .onEach { repoUpdateMutableStateResultFlow.emit(it) }
            .launchIn(ioScope)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
