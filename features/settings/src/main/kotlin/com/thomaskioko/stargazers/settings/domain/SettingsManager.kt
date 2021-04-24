package com.thomaskioko.stargazers.settings.domain

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.thomaskioko.stargazer.core.ViewStateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class SettingsManager @Inject constructor(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = PREFS_NAME)

    fun getUiModeFlow(): Flow<ViewStateResult<Int>> = context.dataStore.data
        .catch {
            if (it is IOException) emit(emptyPreferences())
            else throw it
        }
        .map { preference ->
            when (preference[IS_DARK_MODE] ?: false) {
                true -> ViewStateResult.success(AppCompatDelegate.MODE_NIGHT_YES)
                false -> ViewStateResult.success(AppCompatDelegate.MODE_NIGHT_NO)
                else -> ViewStateResult.success(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

    suspend fun setUiMode(nightModeSetting: Int) {
        context.dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = nightModeSetting
        }
    }

    companion object {
        const val PREFS_NAME = "settings_pref"
        val IS_DARK_MODE = intPreferencesKey("dark_mode")
    }
}

enum class UiTheme {
    LIGHT, DARK
}
