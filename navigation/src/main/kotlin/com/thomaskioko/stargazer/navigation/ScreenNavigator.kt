package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SearchReposScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SettingsScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.TrendingRepositoriesScreen
import javax.inject.Inject
import javax.inject.Provider

sealed class NavigationScreen {
    object TrendingRepositoriesScreen : NavigationScreen()
    object SearchReposScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
    object SettingsScreen : NavigationScreen()
    data class RepoDetailsScreen(
        val repoId: Long
    ) : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)

    fun goBack()
}

class ScreenNavigationImpl @Inject constructor(
    private val navControllerProvider: Provider<NavController>
) : ScreenNavigator {

    override fun goToScreen(navigationScreen: NavigationScreen) {
        when (navigationScreen) {
            SearchReposScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionSearchRepos())
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

    override fun goBack() {
        navControllerProvider.get()
            .navigateUp()
    }
}
