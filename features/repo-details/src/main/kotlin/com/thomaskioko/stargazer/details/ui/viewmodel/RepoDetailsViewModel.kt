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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class RepoDetailsViewModel @Inject constructor(
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    val repoMutableStateResultFlow: MutableStateFlow<ViewStateResult<RepoViewDataModel>> =
        MutableStateFlow(ViewStateResult.loading())

    val repoUpdateMutableStateResultFlow: MutableStateFlow<ViewStateResult<RepoViewDataModel>> =
        MutableStateFlow(ViewStateResult.loading())

    fun getRepoById(repoId: Long) {
        viewModelScope.launch(ioDispatcher) {
            getRepoByIdInteractor(repoId)
                .onEach { repoMutableStateResultFlow.value = it }
        }
    }

    fun updateBookmarkState(updateObject: UpdateObject) {
        viewModelScope.launch(ioDispatcher) {
            bookmarkStateInteractor(updateObject)
                .onEach { repoUpdateMutableStateResultFlow.value = it }
        }
    }
}
