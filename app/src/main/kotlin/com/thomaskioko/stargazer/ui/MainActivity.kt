package com.thomaskioko.stargazer.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailsNavScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SettingsNavScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.TrendingRepositoriesNavScreen
import com.thomaskioko.stargazer.navigation.TabScreens
import com.thomaskioko.stargazer.navigation.addNavigation
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var composeNavigationFactories: @JvmSuppressWildcards Set<ComposeNavigationFactory>

    @Inject
    lateinit var flowNetworkObserver: FlowNetworkObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                val navController = rememberNavController()

                val bottomNavigationItems = listOf(
                    TabScreens.Trending,
                    TabScreens.Search,
                    TabScreens.Favorite,
                )
                val route = currentRoute(navController)

                StargazerTheme {
                    Scaffold(
                        bottomBar = {
                            when {
                                route != SettingsNavScreen.route && !route.contains(
                                    RepoDetailsNavScreen.route
                                ) -> {
                                    StargazersBottomNavigation(
                                        navController,
                                        bottomNavigationItems
                                    )
                                }
                            }
                        },
                        content = {
                            NavHost(
                                navController,
                                startDestination = TrendingRepositoriesNavScreen.route
                            ) {
                                composeNavigationFactories.addNavigation(this, navController)
                            }
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun StargazersBottomNavigation(
        navController: NavHostController,
        items: List<TabScreens>
    ) {
        BottomNavigation {
            val currentRoute = currentRoute(navController)
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(id = screen.resourceId)) },
                    selected = currentRoute == screen.route,
                    alwaysShowLabel = false,
                    selectedContentColor = MaterialTheme.colors.secondary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun currentRoute(navController: NavHostController): String {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route ?: TrendingRepositoriesNavScreen.route
    }
}
