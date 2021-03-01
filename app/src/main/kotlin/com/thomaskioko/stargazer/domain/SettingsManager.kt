package com.thomaskioko.stargazer.domain

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class SettingsManager @Inject constructor(context: Context) {

    private val dataStore = context.createDataStore(name = PREFS_NAME)

    fun getUiModeFlow(): Flow<UiTheme> = dataStore.data
        .catch {
            if (it is IOException) emit(emptyPreferences()) else throw it
        }
        .map { preference ->
            when (preference[IS_DARK_MODE] ?: false) {
                true -> UiTheme.DARK
                false -> UiTheme.LIGHT
            }
        }


    suspend fun setUiMode(uiTheme: UiTheme) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = when (uiTheme) {
                UiTheme.LIGHT -> false
                UiTheme.DARK -> true
            }
        }
    }

    companion object {
        const val PREFS_NAME = "settings_pref"
        val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}

enum class UiTheme {
    LIGHT, DARK
}

