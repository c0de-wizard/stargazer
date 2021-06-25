package com.thomaskioko.stargazers.settings.ui

import androidx.lifecycle.viewModelScope
import com.thomaskioko.stargazer.core.injection.annotations.IoDispatcher
import com.thomaskioko.stargazer.core.viewmodel.BaseViewModel
import com.thomaskioko.stargazers.settings.domain.StargazerPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreference: StargazerPreferences,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<SettingsActions, SettingsViewState>(
    initialViewState = SettingsViewState.Init,
    dispatcher = ioDispatcher
) {

    override fun handleAction(action: SettingsActions) {
        when (action) {
            is SettingsActions.UpdateTheme -> {
                viewModelScope.launch(context = ioDispatcher) {
                    themePreference.emitTheme(action.nightModeSetting)
                }
            }
        }
    }
}
