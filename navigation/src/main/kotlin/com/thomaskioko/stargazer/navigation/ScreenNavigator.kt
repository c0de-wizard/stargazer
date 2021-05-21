package com.thomaskioko.stargazer.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.flow.MutableStateFlow


sealed class TabScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Trending : TabScreens(ScreenDirections.Trending.destination, R.string.menu_item_trending, Icons.Filled.List)
    object Search : TabScreens(ScreenDirections.Search.destination, R.string.menu_item_search, Icons.Filled.Search)
    object Favorite : TabScreens(ScreenDirections.Favorite.destination, R.string.menu_item_favorite, Icons.Filled.Bookmark)
}

interface NavigationCommand {
    val destination: String
}

object ScreenDirections {
    val Default = object : NavigationCommand {
        override val destination = ""
    }

    val Settings = object : NavigationCommand {
        override val destination = "settings"
    }

    val Details = object : NavigationCommand {
        override val destination = "details"
    }

    val Trending = object : NavigationCommand {
        override val destination = "trending"
    }

    val Favorite = object : NavigationCommand {
        override val destination = "favorite"
    }

    val Search = object : NavigationCommand {
        override val destination = "search"
    }
}

class ScreenNavigationManager {

    var directionsList = MutableStateFlow(ScreenDirections.Default)

    fun navigate(directions: NavigationCommand) {
        directionsList.value = directions
    }
}
