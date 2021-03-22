package com.thomaskioko.stargazers.settings.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme(darkTheme = settingsViewModel.isNightMode()) {
                    SettingsScreen(
                        onThemeChanged = { settingsViewModel.setNightMode(!settingsViewModel.isNightMode()) },
                        onBackPressed = { activity?.onBackPressed() }
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingsViewModel.isNightMode.observe(viewLifecycleOwner) { isNightMode ->
            val nightModeSetting = if (isNightMode) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(nightModeSetting)
        }
    }
}
