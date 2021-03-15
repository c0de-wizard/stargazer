package com.thomaskioko.stargazer.browse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.MaterialElevationScale
import com.thomaskioko.githubstargazer.browse.R
import com.thomaskioko.githubstargazer.browse.databinding.FragmentRepoListBinding
import com.thomaskioko.stargazer.browse.model.RepoViewDataModel
import com.thomaskioko.stargazer.browse.ui.adapter.RepoItemClick
import com.thomaskioko.stargazer.browse.ui.adapter.RepoListAdapter
import com.thomaskioko.stargazer.browse.ui.viewmodel.GetReposViewModel
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.NavigationScreen.RepoDetailScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.ui.BaseFragment
import com.thomaskioko.stargazers.ui.extensions.hideView
import com.thomaskioko.stargazers.ui.extensions.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RepoListFragment : BaseFragment<FragmentRepoListBinding>() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: GetReposViewModel by viewModels()
    private lateinit var repoListAdapter: RepoListAdapter

    private var uiStateJob: Job? = null

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            val transitionName = getString(R.string.repo_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to transitionName)

            screenNavigator.goToScreen(RepoDetailScreen(repoId, extras))
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
    ): FragmentRepoListBinding = FragmentRepoListBinding.inflate(inflater, container, false)
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

        with(binding) {
            viewmodel?.let {

                getRepoViewModel.getRepoList()

                uiStateJob = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                    it.repoList
                        .collect { result -> handleResult(result) }
                }
            }
        }
    }

    override fun onStop() {
        // Stop collecting when the View goes to the background
        uiStateJob?.cancel()
        super.onStop()
    }

    private fun handleResult(viewStateResult: ViewStateResult<List<RepoViewDataModel>>) {
        when (viewStateResult) {
            is ViewStateResult.Success -> {
                binding.loadingBar.hideView()
                viewStateResult.data.let {
                    repoListAdapter.itemsList = it
                }
            }
            is ViewStateResult.Loading -> binding.loadingBar.showView()
            is ViewStateResult.Error -> {
                binding.loadingBar.hideView()
                binding.tvInfo.showView()
                binding.tvInfo.text = viewStateResult.message
                Timber.e(viewStateResult.message)
            }
        }
    }

}
