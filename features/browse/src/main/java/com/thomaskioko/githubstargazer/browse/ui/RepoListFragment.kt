package com.thomaskioko.githubstargazer.browse.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.browse.databinding.FragmentRepoListBinding
import com.thomaskioko.githubstargazer.browse.ui.adapter.RepoItemClick
import com.thomaskioko.githubstargazer.browse.ui.adapter.RepoListAdapter
import com.thomaskioko.githubstargazer.browse.ui.viewmodel.GetReposViewModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.hideView
import com.thomaskioko.githubstargazer.core.extensions.showView
import com.thomaskioko.githubstargazer.core.util.ConnectivityUtil.isConnected
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import timber.log.Timber

@AndroidEntryPoint
class RepoListFragment : Fragment() {

    private val getRepoViewModel: GetReposViewModel by viewModels()

    private var _binding: FragmentRepoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repoListAdapter: RepoListAdapter

    private var uiStateJob: Job? = null

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            val action =
                RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(repoId)
            view.findNavController().navigate(action, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepoListBinding.inflate(inflater, container, false).apply {

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

        with(binding) {
            viewmodel?.let {

                getRepoViewModel.getRepoList(isConnected(requireActivity()))

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun handleResult(viewState: ViewState<List<RepoViewDataModel>>) {
        when (viewState) {
            is ViewState.Success -> {
                binding.loadingBar.hideView()
                viewState.data.let {
                    repoListAdapter.setData(it as ArrayList)
                }
            }
            is ViewState.Loading -> binding.loadingBar.showView()
            is ViewState.Error -> {
                binding.loadingBar.hideView()
                binding.tvInfo.showView()
                binding.tvInfo.text = viewState.message
                Timber.e(viewState.message)
            }
        }
    }
}
