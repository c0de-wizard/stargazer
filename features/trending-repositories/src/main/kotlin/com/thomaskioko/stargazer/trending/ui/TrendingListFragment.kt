package com.thomaskioko.stargazer.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.MaterialElevationScale
import com.thomaskioko.stargazer.core.factory.create
import com.thomaskioko.stargazer.core.viewmodel.observe
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazer.trending.R
import com.thomaskioko.stargazer.trending.databinding.FragmentTrendingRepositoriesBinding
import com.thomaskioko.stargazer.trending.ui.ReposAction.NavigateToRepoDetail
import com.thomaskioko.stargazers.ui.BaseFragment
import com.thomaskioko.stargazers.ui.extensions.showView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class TrendingListFragment : BaseFragment<FragmentTrendingRepositoriesBinding>() {

    @Inject
    lateinit var factory: GetRepoListViewModel.Factory

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: GetRepoListViewModel by viewModels {
        create(factory, screenNavigator)
    }

    private lateinit var repoListAdapter: RepoListAdapter

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            val transitionName = getString(R.string.repo_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to transitionName)

            getRepoViewModel.dispatchAction(NavigateToRepoDetail(repoId, extras))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTrendingRepositoriesBinding = FragmentTrendingRepositoriesBinding
        .inflate(inflater, container, false)
        .apply {
            viewmodel = getRepoViewModel

            repoList.apply {
                repoListAdapter = RepoListAdapter(onRepoItemClick)
                adapter = repoListAdapter
            }
        }

    override fun getToolbar(): Toolbar = binding.toolbarLayout.toolbar

    override fun navigateToSettingsScreen() {
        screenNavigator.goToScreen(NavigationScreen.SettingsScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        lifecycleScope.launchWhenStarted {
            getRepoViewModel.actionState.observe(viewLifecycleOwner) { render(it) }
        }
    }

    private fun render(state: ReposViewState) {

        binding.loadingBar.isVisible =
            state is ReposViewState.Loading || state is ReposViewState.Error

        when (state) {
            is ReposViewState.ResultRepoList -> repoListAdapter.apply { itemsList = state.list }
            is ReposViewState.Error -> {
                binding.tvInfo.showView()
                binding.tvInfo.text = state.message
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getRepoViewModel.dispatchAction(ReposAction.LoadRepositories)
    }
}
