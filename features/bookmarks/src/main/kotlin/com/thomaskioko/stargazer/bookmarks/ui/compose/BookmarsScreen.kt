package com.thomaskioko.stargazer.bookmarks.ui.compose

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import com.thomaskioko.stargazer.bookmarks.R
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.LoadRepositories
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToRepoDetailScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkActions.NavigateToSettingsScreen
import com.thomaskioko.stargazer.bookmarks.ui.BookmarkViewState
import com.thomaskioko.stargazer.bookmarks.ui.viewmodel.GetBookmarkedReposViewModel
import com.thomaskioko.stargazers.common.compose.components.AppBarImageVectorIcon
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazersScaffold
import com.thomaskioko.stargazers.common.compose.components.StargazersTopBar
import com.thomaskioko.stargazers.common.compose.components.mockdata.RepoRepository.getRepositoryList
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineScope

@Composable
internal fun BookmarksScreen(
    viewModel: GetBookmarkedReposViewModel,
    navController: NavHostController
) {
    viewModel.dispatchAction(LoadRepositories)

    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val lifecycleOwner = LocalLifecycleOwner.current
    val actionState = viewModel.stateFlow

    val actionStateLifeCycleAware = remember(actionState, lifecycleOwner) {
        actionState.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }

    val repoViewState by actionStateLifeCycleAware
        .collectAsState(initial = BookmarkViewState.Loading)

    StargazersScaffold(
        scaffoldState = scaffoldState,
        appBar = {
            StargazersTopBar(
                title = { Row { Text(text = stringResource(R.string.title_bookmarks)) } },
                actions = {
                    AppBarImageVectorIcon(
                        icon = Icons.Filled.Settings,
                        onClickAction = { viewModel.dispatchAction(NavigateToSettingsScreen) }
                    )
                }
            )
        },
        content = { innerPadding ->
            ScreenContent(
                repoViewState,
                scaffoldState,
                coroutineScope,
                innerPadding,
                onErrorActionRetry = { viewModel.dispatchAction(LoadRepositories) },
                onItemClicked = { viewModel.dispatchAction(NavigateToRepoDetailScreen(it)) }
            )
        }
    )
}

@Composable
internal fun ScreenContent(
    repoViewState: BookmarkViewState,
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    innerPadding: PaddingValues,
    onErrorActionRetry: () -> Unit,
    onItemClicked: (Long) -> Unit
) {

    when (repoViewState) {
        BookmarkViewState.Loading -> CircularLoadingView()
        is BookmarkViewState.Error -> SnackBarErrorRetry(
            snackBarHostState = scaffoldState.snackbarHostState,
            coroutineScope = coroutineScope,
            errorMessage = repoViewState.message,
            onErrorAction = onErrorActionRetry
        )
        is BookmarkViewState.Success -> TrendingRepositoryList(
            repoList = repoViewState.list,
            onRepoItemClicked = onItemClicked,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@Composable
fun TrendingRepositoryList(
    modifier: Modifier = Modifier,
    repoList: List<RepoViewDataModel>,
    onRepoItemClicked: (Long) -> Unit = { }
) {
    when {
        repoList.isEmpty() -> EmptyListView()
        else -> RepoListView(modifier, repoList, onRepoItemClicked)
    }
}

@Composable
private fun RepoListView(
    modifier: Modifier,
    repoList: List<RepoViewDataModel>,
    onRepoItemClicked: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(repoList) { repo ->
            RepoCardItem(
                repo = repo,
                onRepoItemClicked = { onRepoItemClicked(repo.repoId) })
            RepoListDivider()
        }
    }
}

@Composable
private fun EmptyListView() {
    TextButton(onClick = { }, Modifier.fillMaxSize()) {
        Text(
            text = "Why you no have bookmarks? 😤",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.secondaryVariant,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(
    name = "Bookmarked Repositories",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "Bookmarked Repositories • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun RepoListScreenPreview() {
    StargazerTheme {
        Surface {
            TrendingRepositoryList(
                repoList = getRepositoryList()
            )
        }
    }
}

@Preview(
    name = "Empty Repositories",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "Empty Repositories • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun EmptyListScreenPreview() {
    StargazerTheme {
        Surface {
            TrendingRepositoryList(
                repoList = emptyList()
            )
        }
    }
}
