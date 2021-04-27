package com.thomaskioko.stargazer.browse.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.thomaskioko.githubstargazer.browse.R
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.browse.ui.viewmodel.SearchReposViewModel
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.LoadingItem
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.components.SearchBar
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazersScaffold
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flowOf


@Composable
internal fun SearchScreen(
    viewModel: SearchReposViewModel,
    onSearch: (String) -> Unit = { },
    onRepoClicked: (Long) -> Unit = { },
    onBackPressed: () -> Unit = { },
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
        .collectAsState(initial = SearchViewState.Init)

    StargazersScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            SearchTopBarContent(
                onSearch = onSearch,
                onBackPressed = onBackPressed
            )
        }
    ) { innerPadding ->

        SearchScreenContent(
            repoViewState,
            scaffoldState,
            coroutineScope,
            onErrorActionRetry,
            onRepoClicked,
            innerPadding
        )
    }
}

@Composable
private fun SearchScreenContent(
    repoViewState: SearchViewState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    onErrorActionRetry: () -> Unit,
    onItemClicked: (Long) -> Unit,
    innerPadding: PaddingValues
) {
    when (repoViewState) {
        SearchViewState.Init -> {
        }
        SearchViewState.Loading -> CircularLoadingView()
        is SearchViewState.Error -> SnackBarErrorRetry(
            snackBarHostState = scaffoldState.snackbarHostState,
            coroutineScope = coroutineScope,
            errorMessage = repoViewState.message,
            onErrorAction = onErrorActionRetry
        )
        is SearchViewState.Success -> {
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
                        this@LazyColumn.item { LoadingItem() }
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SearchTopBarContent(
    onSearch: (String) -> Unit = { },
    onBackPressed: () -> Unit = { },
) {

    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = MaterialTheme.colors.primary,
            elevation = 8.dp,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_back),
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier
                        .clickable(onClick = { onBackPressed() })
                        .padding(16.dp)
                )

                SearchBar(
                    onSearch = onSearch,
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp)
                        .weight(1f)
                )
            }
        }
    }
}

@Preview(
    name = "SearchTopBar",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "SearchTopBar • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SearchTopBarPreview() {
    val scaffoldState = rememberScaffoldState()
    StargazerTheme {
        Surface {
            StargazersScaffold(
                scaffoldState = scaffoldState,
                appBar = {
                    SearchTopBarContent(
                        onSearch = {  },
                        onBackPressed = {  }
                    )
                }
            ) {
            }
        }
    }
}