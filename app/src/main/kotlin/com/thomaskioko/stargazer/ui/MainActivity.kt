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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.thomaskioko.stargazer.bookmarks.ui.compose.BookmarksScreen
import com.thomaskioko.stargazer.browse.ui.compose.SearchScreen
import com.thomaskioko.stargazer.core.network.FlowNetworkObserver
import com.thomaskioko.stargazer.details.ui.compose.RepoDetailsScreen
import com.thomaskioko.stargazer.navigation.ScreenDirections.Details
import com.thomaskioko.stargazer.navigation.ScreenDirections.Favorite
import com.thomaskioko.stargazer.navigation.ScreenDirections.Search
import com.thomaskioko.stargazer.navigation.ScreenDirections.Settings
import com.thomaskioko.stargazer.navigation.ScreenDirections.Trending
import com.thomaskioko.stargazer.navigation.ScreenNavigationManager
import com.thomaskioko.stargazer.navigation.TabScreens
import com.thomaskioko.stargazer.trending.ui.compose.TrendingRepositoryScreen
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.settings.domain.SettingsManager
import com.thomaskioko.stargazers.settings.ui.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var screenNavigationManager: ScreenNavigationManager

    @Inject
    lateinit var settingsManager: SettingsManager


    @Inject
    lateinit var flowNetworkObserver: FlowNetworkObserver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProvideWindowInsets(consumeWindowInsets = false) {
                val navController = rememberNavController()

                screenNavigationManager.directionsList.collectAsState().value.also { command ->
                    if (command.destination.isNotEmpty()) {
                        navController.navigate(command.destination)
                    }
                }

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
                                route != Settings.destination && !route.contains(Details.destination) -> {
                                    StargazersBottomNavigation(
                                        navController,
                                        bottomNavigationItems
                                    )
                                }
                            }
                        },
                    ) {
                        StargazerNavHost(navController)
                    }
                }
            }
        }
    }

    @Composable
    private fun StargazerNavHost(
        navController: NavHostController
    ) {
        NavHost(navController, startDestination = Trending.destination) {

            composable(Trending.destination) {
                TrendingRepositoryScreen(
                    onItemClicked = { repoId ->
                        navController.navigate("${Details.destination}/$repoId")
                    }
                )
            }

            composable(Search.destination) {
                SearchScreen()
            }
            composable(Favorite.destination) {
                BookmarksScreen()
            }

            composable(Settings.destination) {
                SettingsScreen(
                    onBackPressed = { navController.navigateUp() }
                )
            }

            composable(
                route = "${Details.destination}/{repoId}",
                arguments = listOf(navArgument("repoId") { type = NavType.LongType })
            ) { entry ->
                RepoDetailsScreen(
                    onBackPressed = { navController.navigateUp() },
                    repoId = entry.arguments?.getLong("repoId")
                )
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
        return navBackStackEntry?.destination?.route ?: Trending.destination
    }
}
