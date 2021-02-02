package com.thomaskioko.githubstargazer.repo_details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.repo_details.databinding.FragmentRepoDetailsBinding
import com.thomaskioko.githubstargazer.repo_details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {

    private val args: RepoDetailsFragmentArgs by navArgs()
    private val getRepoViewModel: RepoDetailsViewModel by viewModels()

    private lateinit var binding: FragmentRepoDetailsBinding
    private lateinit var viewDataModel: RepoViewDataModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false).apply {
            viewmodel = getRepoViewModel
        }.apply { floatingActionButton.setOnClickListener { handleButtonClick() } }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel?.let {

            getRepoViewModel.getRepoById(args.repoId)
            it.repoMutableStateFlow
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)

            it.repoUpdateMutableStateFlow
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)
        }
    }

    private fun handleButtonClick() {
        val isBookmarked = if (viewDataModel.isBookmarked) viewDataModel.isBookmarked else true
        binding.viewmodel?.let {
            it.updateBookmarkState(
                com.thomaskioko.githubstargazer.repo_details.domain.model.UpdateObject(
                    args.repoId,
                    isBookmarked
                )
            )
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
