package com.thomaskioko.stargazers.settings.domain

import kotlinx.coroutines.flow.Flow

interface StargazerPreferences {

    fun setup()

    fun observeTheme(): Flow<Theme>
    fun emitTheme(themeValue: String)

    enum class Theme {
        LIGHT,
        DARK,
        SYSTEM
    }
}
