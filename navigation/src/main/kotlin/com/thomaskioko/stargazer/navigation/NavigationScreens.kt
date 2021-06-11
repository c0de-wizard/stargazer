package com.thomaskioko.stargazer.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListNavScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.SearchReposNavScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.TrendingRepositoriesNavScreen

sealed class NavigationScreen(val route: String) {
    object TrendingRepositoriesNavScreen : NavigationScreen("trending")
    object SearchReposNavScreen : NavigationScreen("search")
    object BookmarkListNavScreen : NavigationScreen("bookmarks")
    object SettingsNavScreen : NavigationScreen("settings")
    object RepoDetailsNavScreen : NavigationScreen( "details")
}

sealed class TabScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Trending : TabScreens(TrendingRepositoriesNavScreen.route, R.string.menu_item_trending, Icons.Filled.List)
    object Search : TabScreens(SearchReposNavScreen.route, R.string.menu_item_search, Icons.Filled.Search)
    object Favorite : TabScreens(BookmarkListNavScreen.route, R.string.menu_item_favorite, Icons.Filled.Bookmark)
}
