package com.thomaskioko.stargazer.navigation

import android.annotation.SuppressLint
import android.app.Activity
import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen

sealed class NavigationScreen {
    object RepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
}

interface ScreenNavigator {
    fun goToScreen(navigationScreen: NavigationScreen)
}

interface NavigationDependencies {
    fun screenNavigator(): ScreenNavigator
}

class Navigator {
    lateinit var navController: NavController

    fun navigateToScreen(navigationFlow: NavigationScreen) = when (navigationFlow) {
        RepoListScreen -> navController.navigate(MainNavGraphDirections.actionRepoList())
        BookmarkListScreen -> navController.navigate(MainNavGraphDirections.actionBookmarkList())
    }
}

const val NAVIGATION_DEPS_SERVICE = "com.thomaskioko.stargazer.navigation"

fun Activity.navigationDeps(): NavigationDependencies {
    @SuppressLint("WrongConstant")
    val navigationDeps = getSystemService(NAVIGATION_DEPS_SERVICE) as? NavigationDependencies
        ?: applicationContext.getSystemService(NAVIGATION_DEPS_SERVICE) as? NavigationDependencies

    return navigationDeps ?: throw NullPointerException(
        "Activity must override getSystemService and provide NavigationDeps " +
                "for service name: $NAVIGATION_DEPS_SERVICE"
    )
}
