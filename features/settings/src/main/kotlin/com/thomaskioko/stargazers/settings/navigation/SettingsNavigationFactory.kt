package com.thomaskioko.stargazers.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.viewModelComposable
import com.thomaskioko.stargazers.settings.ui.SettingsScreen
import com.thomaskioko.stargazers.settings.ui.SettingsViewModel
import javax.inject.Inject

internal class SettingsNavigationFactory @Inject constructor() : ComposeNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<SettingsViewModel>(
            route = NavigationScreen.SettingsNavScreen.route,
            content = {
                SettingsScreen(
                    viewModel = this,
                    navController = navController
                )
            }
        )
    }

}
