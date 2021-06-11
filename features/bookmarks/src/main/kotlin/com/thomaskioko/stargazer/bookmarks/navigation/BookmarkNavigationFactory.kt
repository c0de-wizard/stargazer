package com.thomaskioko.stargazer.bookmarks.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.bookmarks.ui.compose.BookmarksScreen
import com.thomaskioko.stargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.stargazer.navigation.ComposeNavigationFactory
import com.thomaskioko.stargazer.navigation.NavigationScreen.BookmarkListNavScreen
import com.thomaskioko.stargazer.navigation.viewModelComposable
import javax.inject.Inject

internal class BookmarkNavigationFactory @Inject constructor() : ComposeNavigationFactory {

    override fun create(builder: NavGraphBuilder, navController: NavHostController) {
        builder.viewModelComposable<GetBookmarkedReposViewModel>(
            route = BookmarkListNavScreen.route,
            content = {
                BookmarksScreen(
                    viewModel = this,
                    navController = navController
                )
            }
        )
    }

}
