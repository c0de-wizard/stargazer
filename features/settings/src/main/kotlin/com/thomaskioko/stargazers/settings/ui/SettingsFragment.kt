package com.thomaskioko.stargazers.settings.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.google.android.material.appbar.MaterialToolbar
import com.thomaskioko.stargazers.settings.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    PreferenceManager.OnPreferenceTreeClickListener {

    @Inject
    lateinit var navControllerProvider: Provider<NavController>

    private val navController: NavController
        get() = navControllerProvider.get()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar_fragment_simple)
        toolbar?.let {
            setupToolbarWithNav(it)
            setupToolbarWithPadding(it)
        }
    }


    private fun setupToolbarWithNav(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.repoListFragment,
                R.id.trendingListFragment,
                R.id.bookmarkListFragment
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun setupToolbarWithPadding(toolbar: Toolbar) {
        //set the toolbar title
        val toolbarTextView = toolbar.findViewById<TextView>(R.id.toolbar_title)
        toolbarTextView.text = toolbar.title
        //apply padding to fit under translucent status bar
        setupEdgeToEdgePadding(toolbar)
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
                    "MODE_NIGHT_YES"
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "MODE_NIGHT_NO"
                }
            }
            LIST_THEME_PREFERENCE_KEY -> {
                val value: Int = (newValue as String).toInt()
                AppCompatDelegate.setDefaultNightMode(value)
                val themeMode = when (value) {
                    -1 -> "MODE_NIGHT_FOLLOW_SYSTEM"
                    2 -> "MODE_NIGHT_YES"
                    1 -> "MODE_NIGHT_NO"
                    else -> "default (unchanged)"
                }
            }
        }
        return true
    }

    companion object {
        const val LIST_THEME_PREFERENCE_KEY = "theme_list"
        const val SWITCH_THEME_PREFERENCE_KEY = "theme_switch"
    }
}
