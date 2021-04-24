package com.thomaskioko.stargazer.bookmarks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.LoadRepositories
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToSettingsScreen
import com.thomaskioko.stargazer.bookmarks.ui.compose.BookmarksScreen
import com.thomaskioko.stargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.stargazer.core.factory.create
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class BookmarkedReposFragment : Fragment() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    @Inject
    lateinit var factory: GetBookmarkedReposViewModel.Factory

    private val viewModel: GetBookmarkedReposViewModel by viewModels {
        create(factory, screenNavigator)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme {
                    BookmarksScreen(
                        viewModel = viewModel,
                        onSettingsPressed = { viewModel.dispatchAction(NavigateToSettingsScreen) },
                        onErrorActionRetry = { viewModel.dispatchAction(LoadRepositories) },
                        onRepoClicked = { viewModel.dispatchAction(NavigateToRepoDetailScreen(it)) }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.dispatchAction(LoadRepositories)
    }
}
