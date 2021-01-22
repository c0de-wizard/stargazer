package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.*
import javax.inject.Inject

sealed class NavigationScreen {
    object RepoListScreen : NavigationScreen()
    object MviRepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
    data class RepoDetailScreen(val repoId: Long) : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)
}

class Navigator {
    lateinit var navController: NavController

    fun navigateToScreen(navigationScreen: NavigationScreen) = when (navigationScreen) {
        RepoListScreen -> navController.navigate(MainNavGraphDirections.actionRepoList())
        BookmarkListScreen -> navController.navigate(MainNavGraphDirections.actionBookmarkList())
        MviRepoListScreen -> navController.navigate(MainNavGraphDirections.actionMviRepoList())
        is RepoDetailScreen -> navController.navigate(
            MainNavGraphDirections.actionRepoDetails(navigationScreen.repoId)
        )
    }
}

class ScreenNavigatorImpl @Inject constructor(
    private val navController: NavController
) : ScreenNavigator {

    override fun goToScreen(navigationScreen: NavigationScreen) {
        when (navigationScreen) {
            RepoListScreen -> navController.navigate(MainNavGraphDirections.actionRepoList())
            BookmarkListScreen -> navController.navigate(MainNavGraphDirections.actionBookmarkList())
            MviRepoListScreen -> navController.navigate(MainNavGraphDirections.actionMviRepoList())
            is RepoDetailScreen -> navController.navigate(
                MainNavGraphDirections.actionRepoDetails(
                    navigationScreen.repoId
                )
            )
        }
    }
}
