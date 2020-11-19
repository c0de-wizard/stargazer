package com.thomaskioko.githubstargazer.browse.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.thomaskioko.githubstargazer.browse.databinding.FragmentRepoListBinding
import com.thomaskioko.githubstargazer.browse.injection.component.inject
import com.thomaskioko.githubstargazer.browse.ui.adapter.RepoItemClick
import com.thomaskioko.githubstargazer.browse.ui.adapter.RepoListAdapter
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.injectViewModel
import com.thomaskioko.githubstargazer.core.util.ConnectivityUtil.isConnected
import com.thomaskioko.githubstargazer.core.viewmodel.AppViewModelFactory
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class RepoListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private lateinit var binding: FragmentRepoListBinding

    private lateinit var repoListAdapter: RepoListAdapter

    private val onRepoItemClick = object : RepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            val action =
                RepoListFragmentDirections.actionRepoListFragmentToRepoDetailFragment(repoId)
            view.findNavController().navigate(action, null)
        }
    }

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRepoListBinding.inflate(inflater, container, false).apply {

            viewmodel = injectViewModel(viewModelFactory)

            repoList.apply {
                repoListAdapter = RepoListAdapter(onRepoItemClick)
                adapter = repoListAdapter
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel?.let {
            it.connectivityAvailable = isConnected(requireActivity())
            it.getRepos()
                .onEach(::handleResult)
                .launchIn(lifecycleScope)
        }
    }

    private fun handleResult(viewState: ViewState<List<RepoViewDataModel>>) {
        when (viewState) {
            is ViewState.Success -> {
                binding.loadingBar.visibility = View.GONE
                viewState.data.let {
                    repoListAdapter.setData(it as ArrayList)
                }
            }
            is ViewState.Loading -> binding.loadingBar.visibility = View.VISIBLE
            is ViewState.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.tvInfo.visibility = View.VISIBLE
                binding.tvInfo.text = viewState.message
                Timber.e(viewState.message)
            }
        }
    }
}
