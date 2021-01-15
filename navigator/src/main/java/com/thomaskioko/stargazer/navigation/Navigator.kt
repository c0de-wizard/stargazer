package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen
import javax.inject.Inject

sealed class NavigationScreen {
    object RepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)
}

class Navigator {
    lateinit var navController: NavController

    fun navigateToScreen(navigationFlow: NavigationScreen) = when (navigationFlow) {
        RepoListScreen -> navController.navigate(MainNavGraphDirections.actionRepoList())
        BookmarkListScreen -> navController.navigate(MainNavGraphDirections.actionBookmarkList())
    }
}

class ScreenNavigatorImpl @Inject constructor(
    private val navController: NavController
) : ScreenNavigator {

    override fun goToScreen(navigationScreen: NavigationScreen) {
        when (navigationScreen) {
            RepoListScreen -> navController.navigate(MainNavGraphDirections.actionRepoList())
            BookmarkListScreen -> navController.navigate(MainNavGraphDirections.actionBookmarkList())
        }
    }
}
