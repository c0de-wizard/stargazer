package com.thomaskioko.stargazer.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.stargazer.core.factory.create
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.trending.ui.ReposAction.LoadRepositories
import com.thomaskioko.stargazer.trending.ui.ReposAction.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.trending.ui.ReposAction.NavigateToSettingsScreen
import com.thomaskioko.stargazer.trending.ui.compose.TrendingRepositoryScreen
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class TrendingListFragment : Fragment() {

    @Inject
    lateinit var factory: GetRepoListViewModel.Factory

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: GetRepoListViewModel by viewModels {
        create(factory, screenNavigator)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                StargazerTheme {
                    val states by getRepoViewModel.actionState.collectAsState(initial = ReposViewState.Loading)
                    TrendingRepositoryScreen(
                        repoViewState = states,
                        onErrorActionRetry = { getRepoViewModel.dispatchAction(LoadRepositories) },
                        onItemClicked = {
                            getRepoViewModel.dispatchAction(
                                NavigateToRepoDetailScreen(
                                    it
                                )
                            )
                        },
                        onSettingsPressed = {
                            getRepoViewModel.dispatchAction(
                                NavigateToSettingsScreen
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getRepoViewModel.dispatchAction(LoadRepositories)
    }
}
