package com.thomaskioko.githubstargazer.browse.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.thomaskioko.githubstargazer.browse.databinding.FragmentRepoDetailBinding
import com.thomaskioko.githubstargazer.browse.domain.model.UpdateObject
import com.thomaskioko.githubstargazer.browse.injection.component.inject
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.injectViewModel
import com.thomaskioko.githubstargazer.core.viewmodel.AppViewModelFactory
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
            viewmodel = injectViewModel(viewModelFactory)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.floatingActionButton.setOnClickListener { handleButtonClick() }

        binding.viewmodel?.let {
            it.repoId = args.repoId
            it.getRepoById()
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)
        }
    }

    private fun handleButtonClick() {
        val isBookmarked = if (viewDataModel.isBookmarked) viewDataModel.isBookmarked else true
        binding.viewmodel?.let {
            it.updateBookmarkState(UpdateObject(args.repoId, isBookmarked))
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)
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
