package com.thomaskioko.githubstargazer.browse_mvi.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.thomaskioko.githubstargazer.browse_mvi.databinding.FragmentMviRepoListBinding
import com.thomaskioko.githubstargazer.core.extensions.showView
import com.thomaskioko.githubstargazer.core.util.ConnectivityUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MviRepoListFragment : Fragment() {

    private val getRepoViewModel: GetRepoListViewModel by viewModels()
    private var _binding: FragmentMviRepoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repoListAdapter: RepoListAdapter

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            getRepoViewModel.dispatchIntent(ReposIntent.RepoItemClicked(repoId))
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
