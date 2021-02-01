package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.*
import javax.inject.Inject
import javax.inject.Provider

sealed class NavigationScreen {
    object RepoListScreen : NavigationScreen()
    object MviRepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
    data class RepoDetailScreen(val repoId: Long) : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)
}

class ScreenNavigatorImpl @Inject constructor(
    private val navController: Provider<NavController>
) : ScreenNavigator {

    override fun goToScreen(navigationScreen: NavigationScreen) {
        when (navigationScreen) {
            RepoListScreen -> navController.get()
                .navigate(MainNavGraphDirections.actionRepoList())
            BookmarkListScreen -> navController.get()
                .navigate(MainNavGraphDirections.actionBookmarkList())
            MviRepoListScreen -> navController.get()
                .navigate(MainNavGraphDirections.actionMviRepoList())
            is RepoDetailScreen -> navController.get()
                .navigate(MainNavGraphDirections.actionRepoDetails(navigationScreen.repoId))
        }
    }
}
