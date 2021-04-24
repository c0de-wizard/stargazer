package com.thomaskioko.stargazer.browse.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.thomaskioko.githubstargazer.browse.R
import com.thomaskioko.stargazer.browse.ui.SearchViewState
import com.thomaskioko.stargazer.browse.ui.viewmodel.SearchReposViewModel
import com.thomaskioko.stargazers.common.compose.components.CircularLoadingView
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.components.SnackBarErrorRetry
import com.thomaskioko.stargazers.common.compose.components.StargazersScaffold
import com.thomaskioko.stargazers.common.model.RepoViewDataModel
import kotlinx.coroutines.CoroutineScope


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
    val query = remember { mutableStateOf(TextFieldValue()) }

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
                textFieldValueState = query,
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
        is SearchViewState.Success -> TrendingRepositoryList(
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

@Composable
private fun SearchTopBarContent(
    textFieldValueState: MutableState<TextFieldValue>,
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
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = stringResource(R.string.cd_back),
                    modifier = Modifier
                        .clickable(onClick = { onBackPressed() })
                        .padding(16.dp)
                )

                OutlinedTextField(
                    value = textFieldValueState.value,
                    onValueChange = {
                        textFieldValueState.value = it
                        onSearch(textFieldValueState.value.text)
                    },
                    label = {
                        Text(
                            text = "Search Repository",
                            color = MaterialTheme.colors.onSecondary
                        )
                    },
                    textStyle = TextStyle(color = MaterialTheme.colors.onSecondary),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colors.onSecondary,
                        textColor = MaterialTheme.colors.onSecondary,
                        disabledTextColor = MaterialTheme.colors.onSecondary,
                        focusedBorderColor = MaterialTheme.colors.onSecondary,
                        unfocusedBorderColor = MaterialTheme.colors.onSecondary,
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSecondary
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { onSearch(textFieldValueState.value.text) }),
                    modifier = Modifier
                        .fillMaxWidth(.9f)
                        .padding(8.dp)
                        .weight(1f),
                )
            }
        }

        LazyColumn {

        }
    }
}
