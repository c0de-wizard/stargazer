package com.thomaskioko.githubstargazer.bookmarks.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.thomaskioko.githubstargazer.bookmarks.R
import com.thomaskioko.githubstargazer.bookmarks.databinding.FragmentBookmarkedReposBinding
import com.thomaskioko.githubstargazer.bookmarks.injection.component.inject
import com.thomaskioko.githubstargazer.bookmarks.ui.adapter.BookmarkRepoItemClick
import com.thomaskioko.githubstargazer.bookmarks.ui.adapter.RepoListAdapter
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.hideView
import com.thomaskioko.githubstargazer.core.extensions.injectViewModel
import com.thomaskioko.githubstargazer.core.extensions.showView
import com.thomaskioko.githubstargazer.core.viewmodel.AppViewModelFactory
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

class BookmarkedReposFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private var _binding: FragmentBookmarkedReposBinding? = null
    private val binding get() = _binding!!
    private lateinit var repoListAdapter: RepoListAdapter

    private val onRepoItemClick = object : BookmarkRepoItemClick {
        override fun onClick(view: View, repoId: Long) {
            // TODO:: update bookmark item to false
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
    ): View {
        _binding = FragmentBookmarkedReposBinding.inflate(inflater, container, false)
            .apply {
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

        with(binding) {
            viewmodel = injectViewModel(viewModelFactory)

            repoList.apply {
                repoListAdapter = RepoListAdapter(onRepoItemClick)
                adapter = repoListAdapter
            }

            viewmodel?.getBookmarkedRepos()?.onEach(::handleResult)?.launchIn(lifecycleScope)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun handleResult(viewState: ViewState<List<RepoViewDataModel>>) {
        when (viewState) {
            is ViewState.Success -> {
                with(binding) {
                    loadingBar.hideView()

                    if (viewState.data.isEmpty()) {
                        tvInfo.showView()
                        tvInfo.text = getString(R.string.empty_list)
                    } else {
                        repoListAdapter.setData(viewState.data as ArrayList)
                    }
                }
            }
            is ViewState.Loading -> binding.loadingBar.visibility = View.VISIBLE
            is ViewState.Error -> {
                Timber.e(viewState.message)

                with(binding) {
                    loadingBar.hideView()
                    tvInfo.showView()
                    tvInfo.text = viewState.message
                }
            }
        }
    }
}
