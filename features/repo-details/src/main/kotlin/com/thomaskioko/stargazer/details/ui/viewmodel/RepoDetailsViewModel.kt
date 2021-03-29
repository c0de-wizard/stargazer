package com.thomaskioko.stargazer.details.ui.viewmodel

import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.ui.DetailAction
import com.thomaskioko.stargazer.details.ui.DetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class RepoDetailsViewModel @Inject constructor(
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<DetailAction, DetailViewState>(
    initialViewState = DetailViewState.Loading,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: DetailAction) {
        when(action) {
            is DetailAction.LoadRepo -> {
                getRepoByIdInteractor(action.repoId)
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .launchIn(ioScope)
            }
            is DetailAction.UpdateRepo -> bookmarkStateInteractor(action.data)
                .onEach { mutableViewState.emit(it.reduce()) }
                .launchIn(ioScope)

        }
    }
}

internal fun ViewStateResult<RepoViewDataModel>.reduce(): DetailViewState {
    return when (this) {
        is ViewStateResult.Loading -> DetailViewState.Loading
        is ViewStateResult.Success -> DetailViewState.Success(data)
        is ViewStateResult.Error -> DetailViewState.Error(message)
    }
}
