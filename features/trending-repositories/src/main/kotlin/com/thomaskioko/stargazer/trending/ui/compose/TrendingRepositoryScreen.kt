package com.thomaskioko.stargazer.trending.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.thomaskioko.stargazer.trending.R
import com.thomaskioko.stargazer.trending.ui.GetRepoListViewModel
import com.thomaskioko.stargazer.trending.ui.ReposViewState
import com.thomaskioko.stargazers.common.compose.components.mockdata.RepoRepository.getRepositoryList
import com.thomaskioko.stargazers.common.compose.components.AppBarSettingsIcon
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazersTopBar
import com.thomaskioko.stargazers.common.compose.model.RepoViewDataModel
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
internal fun TrendingRepositoryScreen(
    viewModel: GetRepoListViewModel,
    onSettingsPressed: () -> Unit = { },
    onItemClicked: (Long) -> Unit = { },
    onErrorActionRetry: () -> Unit = { },
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val actionState = viewModel.stateFlow

    val actionStateLifeCycleAware = remember (actionState, lifecycleOwner){
        actionState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val repoViewState by actionStateLifeCycleAware
        .collectAsState(initial = ReposViewState.Loading)

    Scaffold(
        topBar = {
            StargazersTopBar(
                title = { Row { Text(text = stringResource(R.string.app_name)) } },
                actions = { AppBarSettingsIcon(onSettingsPressed = onSettingsPressed) }
            )
        },
        content = { innerPadding ->
            when (repoViewState) {
                ReposViewState.Loading -> CircularLoadingView()
                is ReposViewState.Error -> SnackBarErrorRetry(
                    errorMessage = (repoViewState as ReposViewState.Error).message,
                    onErrorAction = onErrorActionRetry
                )
                is ReposViewState.Success -> TrendingRepositoryList(
                    repoList = (repoViewState as ReposViewState.Success).list,
                    onRepoItemClicked = onItemClicked,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    )
}

@Composable
fun TrendingRepositoryList(
    modifier: Modifier = Modifier,
    repoList: List<RepoViewDataModel>,
    onRepoItemClicked: (Long) -> Unit = { }
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(repoList) { repo ->
            RepoCardItem(repo = repo, onRepoItemClicked = { onRepoItemClicked(repo.repoId) })
            RepoListDivider()
        }
    }
}


@Preview(
    name = "Trending Repositories",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "Repository Item • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun RepoListScreenPreview() {
    StargazerTheme {
        TrendingRepositoryList(
            repoList = getRepositoryList()
        )
    }
}
