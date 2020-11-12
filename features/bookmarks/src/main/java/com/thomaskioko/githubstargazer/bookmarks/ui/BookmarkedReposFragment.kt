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
import com.thomaskioko.githubstargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.githubstargazer.core.ViewState
import com.thomaskioko.githubstargazer.core.extensions.injectViewModel
import com.thomaskioko.githubstargazer.core.viewmodel.AppViewModelFactory
import com.thomaskioko.stargazer.common_ui.model.RepoViewDataModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class BookmarkedReposFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: AppViewModelFactory

    private lateinit var binding: FragmentBookmarkedReposBinding
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
    ): View? {
        binding = FragmentBookmarkedReposBinding.inflate(inflater, container, false).apply {

            viewmodel = injectViewModel<GetBookmarkedReposViewModel>(viewModelFactory).apply {
                lifecycleScope.launch {
                    getBookmarkedRepos().collect { handleResult(it) }
                }
            }

            repoList.apply {
                repoListAdapter = RepoListAdapter(onRepoItemClick)
                adapter = repoListAdapter
            }
        }

        return binding.root
    }

    private fun handleResult(viewState: ViewState<List<RepoViewDataModel>>) {
        when (viewState) {
            is ViewState.Success -> {
                binding.loadingBar.visibility = View.GONE

                if (viewState.data.isEmpty()) {
                    binding.tvInfo.visibility = View.VISIBLE
                    binding.tvInfo.text = getString(R.string.empty_list)
                } else {
                    repoListAdapter.setData(viewState.data as ArrayList)
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
