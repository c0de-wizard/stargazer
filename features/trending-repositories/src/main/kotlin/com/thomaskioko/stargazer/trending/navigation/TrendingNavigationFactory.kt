package com.thomaskioko.stargazer.trending.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen.TrendingRepositoriesNavScreen
import com.thomaskioko.stargazer.navigation.viewModelComposable
import com.thomaskioko.stargazer.trending.ui.TrendingRepoListViewModel
import com.thomaskioko.stargazer.trending.ui.compose.TrendingRepositoryScreen
import javax.inject.Inject

internal class TrendingNavigationFactory @Inject constructor() : ComposeNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<TrendingRepoListViewModel>(
            route = TrendingRepositoriesNavScreen.route,
            content = {
                TrendingRepositoryScreen(
                    viewModel = this,
                    navController = navController
                )
            }
        )
    }

}
