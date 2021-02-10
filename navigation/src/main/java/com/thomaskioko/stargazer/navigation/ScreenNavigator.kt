package com.thomaskioko.stargazer.navigation

import androidx.navigation.NavController
import com.thomaskioko.stargazer.actions.MainNavGraphDirections
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.MviRepoListScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoListScreen
import javax.inject.Inject
import javax.inject.Provider

sealed class NavigationScreen {
    object RepoListScreen : NavigationScreen()
    object MviRepoListScreen : NavigationScreen()
    object BookmarkListScreen : NavigationScreen()
    data class RepoDetailScreen(val repoId: Long, val extras: String) : NavigationScreen()
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
            MviRepoListScreen -> navControllerProvider.get()
                .navigate(MainNavGraphDirections.actionMviRepoList())
            is RepoDetailScreen -> navControllerProvider.get()
                .navigate(
                    MainNavGraphDirections.actionRepoDetails(
                        navigationScreen.repoId,
                        navigationScreen.extras
                    )
                )
        }
    }
}
