package com.thomaskioko.stargazers.settings.ui

import com.thomaskioko.stargazer.core.presentation.ViewAction
import com.thomaskioko.stargazer.core.presentation.ViewState

sealed class SettingsActions : ViewAction {
    data class UpdateTheme(val nightModeSetting: String) : SettingsActions()
}

sealed class SettingsViewState : ViewState {
    object Init : SettingsViewState()
}
