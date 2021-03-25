package com.thomaskioko.stargazer.trending.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazer.trending.R
import com.thomaskioko.stargazers.common.compose.components.AppBarHomeIcon
import com.thomaskioko.stargazers.common.compose.components.RepoCardItem
import com.thomaskioko.stargazers.common.compose.components.RepoListDivider
import com.thomaskioko.stargazers.common.compose.mockdata.Repo
import com.thomaskioko.stargazers.common.compose.mockdata.RepoRepository.getRepositoryList
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun TrendingRepositoryScreen(
    repoList: List<Repo>
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Row { Text(text = stringResource(R.string.title_settings)) } },
                navigationIcon = { AppBarHomeIcon() },
                backgroundColor = MaterialTheme.colors.primarySurface
            )
        },
        content = { innerPadding ->
            TrendingRepositoryList(
                repoList = repoList,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )
}

@Composable
fun TrendingRepositoryList(
    repoList: List<Repo>,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(repoList) { repo ->
            RepoCardItem(repo = repo)
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
        TrendingRepositoryScreen(
            repoList = getRepositoryList()
        )
    }
}