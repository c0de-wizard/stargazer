package com.thomaskioko.stargazers.settings.ui

import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import com.thomaskioko.stargazers.settings.domain.UiTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsManager: SettingsManager,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsActions, SettingsViewState, CoroutineDispatcher>(
    initialViewState = SettingsViewState.Loading,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: SettingsActions) {
        when (action) {
            SettingsActions.LoadTheme -> {
                settingsManager.getUiModeFlow()
                    .onEach { mutableViewState.emit(it.reduce()) }
                    .launchIn(ioScope)
            }
            is SettingsActions.UpdateTheme -> {
                viewModelScope.launch(context = ioDispatcher) {
                    settingsManager.setUiMode(action.theme)
                }
            }
        }
    }
}

internal fun ViewStateResult<UiTheme>.reduce(): SettingsViewState {
    return when (this) {
        is ViewStateResult.Error -> SettingsViewState.Error("Something went wrong!")
        is ViewStateResult.Loading -> SettingsViewState.Loading
        is ViewStateResult.Success -> SettingsViewState.ThemeLoaded(data)
    }
}
