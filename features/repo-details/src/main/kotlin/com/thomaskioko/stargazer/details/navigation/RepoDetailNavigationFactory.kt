package com.thomaskioko.stargazer.details.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.details.ui.compose.RepoDetailScreen
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailsNavScreen
import com.thomaskioko.stargazer.navigation.viewModelComposable
import javax.inject.Inject

internal class RepoDetailNavigationFactory @Inject constructor() : ComposeNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<RepoDetailsViewModel>(
            route = "${RepoDetailsNavScreen.route}/{repoId}",
            content = {
                RepoDetailScreen(
                    viewModel = this,
                    navController = navController,
                )
            }
        )
    }

}
