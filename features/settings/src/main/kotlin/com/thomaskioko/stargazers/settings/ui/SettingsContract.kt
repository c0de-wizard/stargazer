package com.thomaskioko.stargazers.settings.ui

import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState

sealed class SettingsActions : ViewAction {
    object LoadTheme : SettingsActions()
    data class UpdateTheme(val nightModeSetting: Int) : SettingsActions()
}

sealed class SettingsViewState : ViewState {
    object Loading : SettingsViewState()
    data class ThemeLoaded(val nightModeSetting: Int) : SettingsViewState()
    data class Error(val message: String = "") : SettingsViewState()
}
