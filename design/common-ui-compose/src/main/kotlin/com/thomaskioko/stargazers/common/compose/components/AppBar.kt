package com.thomaskioko.stargazers.common.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.compose.theme.elevatedSurface


@Composable
fun AppBar(
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit) = { AppBarHomeIcon() }
) {
    TopAppBar(
        title = { Row { title() } },
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = MaterialTheme.colors.primarySurface
    )
}

@Composable
fun AppBarScaffold(
    title: @Composable RowScope.() -> Unit,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable (() -> Unit) = { AppBarHomeIcon() },
    content: @Composable () -> Unit
) {

    val backgroundColor = MaterialTheme.colors.elevatedSurface(3.dp)

    Column(
        Modifier.background(backgroundColor.copy(alpha = 0.95f))
    ) {
        StargazersScaffold(
            appBar = {
                TopAppBar(
                    title = { Row { title() } },
                    navigationIcon = navigationIcon,
                    actions = actions,
                    backgroundColor = MaterialTheme.colors.primarySurface
                )
            },
            content = { content() }
        )
    }
}

@Composable
fun AppBarHomeIcon(onNavIconPressed: () -> Unit = { }) {
    Icon(
        painter = painterResource(R.drawable.octocat),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = onNavIconPressed)
            .padding(16.dp)
    )
}

@Composable
fun AppBarBackIcon(onBackPressed: () -> Unit = { }) {
    Icon(
        painter = painterResource(R.drawable.ic_back),
        contentDescription = stringResource(R.string.cd_back),
        modifier = Modifier
            .clickable(onClick = onBackPressed)
            .padding(16.dp)
    )
}

@Preview("AppBar")
@Composable
private fun AppBarPreview() {
    StargazerTheme {
        AppBar(title = { Text("Stargazers") })
    }
}

@Preview("AppBar • Dark")
@Composable
private fun AppBarDarkPreview() {
    StargazerTheme(darkTheme = true) {
        AppBar(title = { Text("Stargazers") })
    }
}

@Preview("AppBar Scaffold")
@Composable
private fun AppBarScaffoldPreview() {
    StargazerTheme {
        AppBarScaffold(title = { Text("Stargazers") }, content = {})
    }
}
