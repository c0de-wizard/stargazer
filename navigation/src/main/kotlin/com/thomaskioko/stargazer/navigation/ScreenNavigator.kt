package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
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
    data class RepoDetailsScreen(
        val repoId: Long
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
            is NavigationScreen.RepoDetailsScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionRepoDetails(navigationScreen.repoId))
        }
    }
}
