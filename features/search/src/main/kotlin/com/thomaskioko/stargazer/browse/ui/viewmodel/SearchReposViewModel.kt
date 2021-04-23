package com.thomaskioko.stargazer.browse.ui.viewmodel

import com.thomaskioko.stargazer.browse.domain.interactor.SearchRepositoriesInteractor
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.browse.ui.SearchAction
import com.thomaskioko.stargazer.browse.ui.SearchAction.SearchRepository
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.DefaultDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class SearchReposViewModel @Inject constructor(
    private val interactor: SearchRepositoriesInteractor,
    @DefaultDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<SearchAction, SearchViewState>(
    initialViewState = SearchViewState.Init,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: SearchAction) {
        when (action) {
            is SearchRepository ->
                interactor(action.query)
                    .debounce(250)
                    .onEach { mutableViewState.emit(it.reduce())  }
                    .stateIn(ioScope, SharingStarted.Eagerly, emptyList<RepoViewDataModel>())
        }
    }
}

internal fun ViewStateResult<List<RepoViewDataModel>>.reduce(): SearchViewState {
    return when (this) {
        is ViewStateResult.Loading -> SearchViewState.Loading
        is ViewStateResult.Success -> SearchViewState.Success(data)
        is ViewStateResult.Error -> SearchViewState.Error(message)
    }
}
