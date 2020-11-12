package com.thomaskioko.githubstargazer.browse.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.thomaskioko.githubstargazer.browse.data.model.UpdateObject
import com.thomaskioko.githubstargazer.browse.databinding.FragmentRepoDetailBinding
import com.thomaskioko.githubstargazer.browse.injection.component.inject
import com.thomaskioko.githubstargazer.browse.ui.viewmodel.GetReposViewModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.injectViewModel
import com.thomaskioko.githubstargazer.core.viewmodel.AppViewModelFactory
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class RepoDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private val args: RepoDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentRepoDetailBinding
    private lateinit var viewDataModel: RepoViewDataModel

    override fun onAttach(context: Context) {
        inject()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = injectViewModel<GetReposViewModel>(viewModelFactory).apply {
                repoId = args.repoId
                lifecycleScope.launch {
                    getRepoById().collect { handleViewStateResult(it) }
                }
                floatingActionButton.setOnClickListener { handleButtonClick(this) }
            }
        }
        return binding.root
    }

    private fun handleButtonClick(viewModel: GetReposViewModel) {
        val isBookmarked = if (viewDataModel.isBookmarked) viewDataModel.isBookmarked else true
        lifecycleScope.launch {
            viewModel.updateBookmarkState(
                UpdateObject(args.repoId, isBookmarked)
            )
                .collect { handleViewStateResult(it) }
        }
    }

    private fun handleViewStateResult(viewState: ViewState<RepoViewDataModel>) {
        when (viewState) {
            is ViewState.Loading -> binding.loadingBar.visibility = View.VISIBLE
            is ViewState.Success -> {
                binding.loadingBar.visibility = View.GONE
                binding.repo = viewState.data
                viewDataModel = viewState.data
            }
            is ViewState.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.tvInfo.visibility = View.VISIBLE
                binding.tvInfo.text = viewState.message
                Timber.e(viewState.message)
            }
        }
    }
}
