package com.thomaskioko.stargazer.browse.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.browse.ui.compose.SearchScreen
import com.thomaskioko.stargazer.browse.ui.viewmodel.SearchReposViewModel
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen.SearchReposNavScreen
import com.thomaskioko.stargazer.navigation.viewModelComposable
import javax.inject.Inject

internal class SearchReposNavigationFactory @Inject constructor() : ComposeNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<SearchReposViewModel>(
            route = SearchReposNavScreen.route,
            content = {
                SearchScreen(
                    viewModel = this,
                    navController = navController
                )
            }
        )
    }

}
