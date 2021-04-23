package com.thomaskioko.stargazer.browse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.stargazer.browse.ui.SearchAction.BackPressed
import com.thomaskioko.stargazer.browse.ui.SearchAction.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.browse.ui.compose.SearchScreen
import com.thomaskioko.stargazer.browse.ui.viewmodel.SearchReposViewModel
import com.thomaskioko.stargazer.core.factory.create
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class SearchReposFragment : Fragment() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    @Inject
    lateinit var factory: SearchReposViewModel.Factory

    private val searchReposViewModel: SearchReposViewModel by viewModels {
        create(factory, screenNavigator)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme {
                    SearchScreen(
                        viewModel = searchReposViewModel,
                        onSearch = { query ->
                            searchReposViewModel.dispatchAction(SearchAction.SearchRepository(query))
                        },
                        onBackPressed = { searchReposViewModel.dispatchAction(BackPressed) },
                        onRepoClicked = {
                            searchReposViewModel.dispatchAction(NavigateToRepoDetailScreen(it))
                        }
                    )
                }
            }
        }
    }

}
