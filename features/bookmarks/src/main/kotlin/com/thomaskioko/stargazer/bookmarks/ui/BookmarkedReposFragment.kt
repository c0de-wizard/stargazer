package com.thomaskioko.stargazer.bookmarks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.stargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkedReposFragment : Fragment() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: GetBookmarkedReposViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme {

                }
            }
        }
    }
}
