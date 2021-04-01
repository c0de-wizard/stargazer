package com.thomaskioko.stargazer.details.ui.viewmodel

import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.factory.AssistedViewModelFactory
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazer.details.domain.GetRepoByIdInteractor
import com.thomaskioko.stargazer.details.domain.UpdateRepoBookmarkStateInteractor
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.ui.DetailAction
import com.thomaskioko.stargazer.details.ui.DetailViewState
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class RepoDetailsViewModel @AssistedInject constructor(
    private val getRepoByIdInteractor: GetRepoByIdInteractor,
    private val bookmarkStateInteractor: UpdateRepoBookmarkStateInteractor,
    @Assisted private val screenNavigator: ScreenNavigator,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<DetailAction, DetailViewState>(
    initialViewState = DetailViewState.Loading,
    dispatcher = ioDispatcher
) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ScreenNavigator> {
        override fun create(data: ScreenNavigator): RepoDetailsViewModel
    }

    override fun handleAction(action: DetailAction) {
        when(action) {
            DetailAction.BackPressed -> screenNavigator.goBack()
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
