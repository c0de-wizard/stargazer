package com.thomaskioko.githubstargazer.browse_mvi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.transition.MaterialElevationScale
import com.thomaskioko.githubstargazer.browse_mvi.R
import com.thomaskioko.githubstargazer.browse_mvi.databinding.FragmentMviRepoListBinding
import com.thomaskioko.githubstargazer.browse_mvi.ui.ReposIntent.RepoItemClicked
import com.thomaskioko.githubstargazer.core.extensions.showView
import com.thomaskioko.githubstargazer.core.factory.create
import com.thomaskioko.githubstargazer.core.util.ConnectivityUtil
import com.thomaskioko.githubstargazer.core.viewmodel.observe
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MviRepoListFragment : Fragment() {

    @Inject
    lateinit var factory: GetRepoListViewModel.Factory

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val getRepoViewModel: GetRepoListViewModel by viewModels {
        create(factory, screenNavigator)
    }
    private var _binding: FragmentMviRepoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repoListAdapter: RepoListAdapter

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            val transitionName = getString(R.string.repo_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to transitionName)

            getRepoViewModel.dispatchIntent(RepoItemClicked(repoId, extras))
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMviRepoListBinding.inflate(inflater, container, false).apply {
            viewmodel = getRepoViewModel

            repoList.apply {
                repoListAdapter = RepoListAdapter(onRepoItemClick)
                adapter = repoListAdapter
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        val isConnected = ConnectivityUtil.isConnected(requireActivity())
        getRepoViewModel.dispatchIntent(ReposIntent.DisplayData(isConnected))

        getRepoViewModel.state.observe(viewLifecycleOwner) { render(it) }
    }

    private fun render(state: ReposViewState) {

        binding.loadingBar.isVisible = state is ReposViewState.Loading

        when (state) {
            ReposViewState.Loading -> binding.loadingBar.isVisible = state is ReposViewState.Loading
            is ReposViewState.ResultRepoList -> repoListAdapter.setData(state.list)
            is ReposViewState.Error -> {
                binding.tvInfo.showView()
                binding.tvInfo.text = state.message
            }
        }
    }
}
