package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import androidx.navigation.Navigator
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SettingsScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.TrendingRepositoriesScreen
import javax.inject.Inject
import javax.inject.Provider

sealed class NavigationScreen {
    object TrendingRepositoriesScreen : NavigationScreen()
    object RepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
    object SettingsScreen : NavigationScreen()
    data class RepoDetailScreen(
        val repoId: Long,
        val extras: Navigator.Extras
    ) : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)
}

class ScreenNavigationImpl @Inject constructor(
    private val navControllerProvider: Provider<NavController>
) : ScreenNavigator {

    override fun goToScreen(navigationScreen: NavigationScreen) {
        when (navigationScreen) {
            RepoListScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionRepoList())
            BookmarkListScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionBookmarkList())
            TrendingRepositoriesScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionTrendingRepos())
            SettingsScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionSettingsFragment())
            is RepoDetailScreen -> navControllerProvider.get()
                .navigate(
                    MainNavGraphDirections.actionRepoDetails(navigationScreen.repoId),
                    navigationScreen.extras
                )
        }
    }
}
