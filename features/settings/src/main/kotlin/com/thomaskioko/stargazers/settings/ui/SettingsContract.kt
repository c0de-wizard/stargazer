package com.thomaskioko.stargazers.settings.ui

import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState
import com.thomaskioko.stargazers.settings.domain.UiTheme

sealed class SettingsActions : ViewAction {
    object LoadTheme : SettingsActions()
    data class UpdateTheme(val theme: UiTheme) : SettingsActions()
}

sealed class SettingsViewState : ViewState {
    object Loading : SettingsViewState()
    data class ThemeLoaded(val uiTheme: UiTheme) : SettingsViewState()
    data class Error(val message: String = "") : SettingsViewState()
}
