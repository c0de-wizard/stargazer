package com.thomaskioko.stargazer.details.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.thomaskioko.stargazer.core.ViewStateResult
import com.thomaskioko.stargazer.details.R
import com.thomaskioko.stargazer.details.databinding.FragmentRepoDetailsBinding
import com.thomaskioko.stargazer.details.domain.model.UpdateObject
import com.thomaskioko.stargazer.details.model.RepoViewDataModel
import com.thomaskioko.stargazer.details.ui.viewmodel.RepoDetailsViewModel
import com.thomaskioko.stargazer.navigation.NavigationScreen
import com.thomaskioko.stargazer.navigation.ScreenNavigator
import com.thomaskioko.stargazers.ui.BaseFragment
import com.thomaskioko.stargazers.ui.extensions.themeColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RepoDetailsFragment : BaseFragment<FragmentRepoDetailsBinding>() {

    @Inject
    lateinit var screenNavigator: ScreenNavigator

    private val args: RepoDetailsFragmentArgs by navArgs()
    private val getRepoViewModel: RepoDetailsViewModel by viewModels()

    private lateinit var viewDataModel: RepoViewDataModel

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRepoDetailsBinding  = FragmentRepoDetailsBinding.inflate(inflater, container, false).apply {
        viewmodel = getRepoViewModel
        floatingActionButton.setOnClickListener { handleButtonClick() }
    }

    override fun getToolbar(): Toolbar = binding.toolbarLayout.toolbar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewmodel?.let {

            getRepoViewModel.getRepoById(args.repoId)

            it.repoMutableStateResultFlow
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)

            it.repoUpdateMutableStateResultFlow
                .onEach(::handleViewStateResult)
                .launchIn(lifecycleScope)
        }
    }

    override fun navigateToSettingsScreen() {
        screenNavigator.goToScreen(NavigationScreen.SettingsScreen)
    }

    private fun handleButtonClick() {
        val isBookmarked = !viewDataModel.isBookmarked
        binding.viewmodel?.let {
            it.updateBookmarkState(
                UpdateObject(args.repoId, isBookmarked)
            )
        }
    }

    private fun handleViewStateResult(viewStateResult: ViewStateResult<RepoViewDataModel>) {
        when (viewStateResult) {
            is ViewStateResult.Loading -> binding.loadingBar.visibility = View.VISIBLE
            is ViewStateResult.Success -> {
                binding.loadingBar.visibility = View.GONE
                binding.repo = viewStateResult.data
                viewDataModel = viewStateResult.data
            }
            is ViewStateResult.Error -> {
                binding.loadingBar.visibility = View.GONE
                binding.tvInfo.visibility = View.VISIBLE
                binding.tvInfo.text = viewStateResult.message
                Timber.e(viewStateResult.message)
            }
        }
    }
}
