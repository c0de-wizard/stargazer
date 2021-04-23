package com.thomaskioko.stargazer.trending.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.thomaskioko.stargazer.trending.R
import com.thomaskioko.stargazer.trending.ui.GetRepoListViewModel
import com.thomaskioko.stargazer.trending.ui.ReposViewState
import com.thomaskioko.stargazers.common.compose.components.AppBarPainterIcon
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.LoadingItem
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazersScaffold
import com.thomaskioko.stargazers.common.compose.components.StargazersTopBar
import com.thomaskioko.stargazers.common.compose.components.mockdata.RepoRepository.getLazyRepositoryList
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun TrendingRepositoryScreen(
    viewModel: GetRepoListViewModel,
    onSettingsPressed: () -> Unit = { },
    onItemClicked: (Long) -> Unit = { },
    onErrorActionRetry: () -> Unit = { },
) {

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val actionState = viewModel.stateFlow

    val actionStateLifeCycleAware = remember(actionState, lifecycleOwner) {
        actionState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val repoViewState by actionStateLifeCycleAware
        .collectAsState(initial = ReposViewState.Loading)

    StargazersScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            StargazersTopBar(
                title = { Row { Text(text = stringResource(R.string.app_name)) } },
                actions = {
                    AppBarPainterIcon(
                        painterResource = painterResource(R.drawable.ic_settings),
                        onClickAction = onSettingsPressed
                    )
                }
            )
        },
        content = { innerPadding ->
            ScreenContent(
                repoViewState,
                scaffoldState,
                coroutineScope,
                onErrorActionRetry,
                onItemClicked,
                innerPadding
            )
        }
    )
}

@Composable
private fun ScreenContent(
    repoViewState: ReposViewState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    onErrorActionRetry: () -> Unit,
    onItemClicked: (Long) -> Unit,
    innerPadding: PaddingValues
) {
    when (repoViewState) {
        ReposViewState.Loading -> CircularLoadingView()
        is ReposViewState.Error -> SnackBarErrorRetry(
            snackBarHostState = scaffoldState.snackbarHostState,
            coroutineScope = coroutineScope,
            errorMessage = repoViewState.message,
            onErrorAction = onErrorActionRetry
        )
        is ReposViewState.Success -> {
            val lazyRepoItems = flowOf(repoViewState.list)
                .cachedIn(coroutineScope)
                .collectAsLazyPagingItems()

            TrendingRepositoryList(
                hostState = scaffoldState.snackbarHostState,
                coroutineScope = coroutineScope,
                lazyRepoItems = lazyRepoItems,
                onRepoItemClicked = onItemClicked,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    }
}

@Composable
fun TrendingRepositoryList(
    hostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    lazyRepoItems: LazyPagingItems<RepoViewDataModel>,
    onRepoItemClicked: (Long) -> Unit = { }
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(lazyRepoItems) { repo ->

            repo?.let {
                RepoCardItem(repo = repo, onRepoItemClicked = { onRepoItemClicked(repo.repoId) })
                RepoListDivider()
            }

            lazyRepoItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        this@LazyColumn.item { CircularLoadingView() }
                    }
                    loadState.append is LoadState.Loading -> {
                        this@LazyColumn.item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val exception = lazyRepoItems.loadState.refresh as LoadState.Error
                        this@LazyColumn.item {
                            SnackBarErrorRetry(
                                snackBarHostState = hostState,
                                coroutineScope = coroutineScope,
                                errorMessage = exception.error.localizedMessage!!,
                                onErrorAction = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val exception = lazyRepoItems.loadState.append as LoadState.Error
                        this@LazyColumn.item {
                            SnackBarErrorRetry(
                                snackBarHostState = hostState,
                                coroutineScope = coroutineScope,
                                errorMessage = exception.error.localizedMessage!!,
                                onErrorAction = { retry() }
                            )
                        }
                    }
                }
            }
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
        val hostState = remember { SnackbarHostState() }
        val coroutineScope = rememberCoroutineScope()

        TrendingRepositoryList(
            hostState = hostState,
            coroutineScope = coroutineScope,
            lazyRepoItems = getLazyRepositoryList().collectAsLazyPagingItems()
        )
    }
}
