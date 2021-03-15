package com.thomaskioko.stargazers.settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.appbar.MaterialToolbar
import com.thomaskioko.stargazer.core.injection.annotations.MainDispatcher
import com.thomaskioko.stargazers.settings.R
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import com.thomaskioko.stargazers.settings.domain.UiTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    PreferenceManager.OnPreferenceTreeClickListener {

    @Inject
    lateinit var navControllerProvider: Provider<NavController>

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    private val navController: NavController
        get() = navControllerProvider.get()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val iconTintColor =
            ContextCompat.getColor(requireContext(), R.color.material_on_surface_disabled)
        val switchPreference = findPreference<SwitchPreferenceCompat>(SWITCH_THEME_PREFERENCE_KEY)
        switchPreference?.let {
            it.icon.setTint(iconTintColor)
            it.onPreferenceChangeListener = this
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_fragment_simple)
        toolbar?.let {
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)

            it.setNavigationOnClickListener { navController.navigateUp() }

            //apply padding to fit under translucent status bar
            setupEdgeToEdgePadding(it)
        }
    }


    private fun setupEdgeToEdgePadding(toolbar: Toolbar) {
        toolbar.setOnApplyWindowInsetsListener { view, insets ->
            val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets)
            val systemWindow = insetsCompat.getInsets(
                WindowInsetsCompat.Type.statusBars()
            )
            view.updatePadding(top = systemWindow.top)
            insets
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any): Boolean {
        when (preference.key) {
            SWITCH_THEME_PREFERENCE_KEY -> {
                val nightModeOn = newValue as Boolean
                val themeMode = if (nightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    UiTheme.DARK
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    UiTheme.LIGHT
                }

                updateTheme(themeMode)
            }
        }
        return true
    }

    private fun updateTheme(themeTheme: UiTheme) {
       val job = GlobalScope.launch(context = mainDispatcher) {
            settingsManager.setUiMode(themeTheme)
        }
        job.cancel()
    }

    companion object {
        const val SWITCH_THEME_PREFERENCE_KEY = "theme_switch"
    }
}
