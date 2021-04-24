package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.common.compose.theme.elevatedSurface


@Composable
fun StargazersTopBar(
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
    Image(
        painter = painterResource(R.drawable.octocat),
        contentDescription = null,
        modifier = Modifier
            .clickable(onClick = onNavIconPressed)
            .padding(16.dp)
    )
}

@Composable
fun AppBarPainterIcon(
    painterResource: Painter,
    onClickAction: () -> Unit = { }
) {
    Icon(
        painter = painterResource,
        contentDescription = stringResource(R.string.cd_back),
        modifier = Modifier
            .clickable(onClick = onClickAction)
            .padding(16.dp)
    )
}

@Composable
fun AppBarImageVectorIcon(
    icon: ImageVector,
    onClickAction: () -> Unit = { }
) {
    Icon(
        imageVector = icon,
        contentDescription = stringResource(R.string.cd_back),
        modifier = Modifier
            .clickable(onClick = onClickAction)
            .padding(16.dp)
    )
}

@Preview(
    name = "AppBar",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "AppBar• Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun AppBarDarkPreview() {
    StargazerTheme {
        StargazersTopBar(title = { Text("Stargazers") })
    }
}

@Preview(
    name = "AppBar Settings",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "AppBar Settings• Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AppBarSettingsPreview() {
    StargazerTheme {
        StargazersTopBar(
            title = { Text("Stargazers") },
            navigationIcon = {
                AppBarPainterIcon(
                    painterResource = painterResource(R.drawable.ic_back),
                    onClickAction = {}
                )
            },
            actions = {
                AppBarPainterIcon(
                    painterResource = painterResource(R.drawable.ic_settings),
                    onClickAction = {}
                )
            },
        )
    }
}

@Preview(
    name = "AppBar Search",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "AppBar Search• Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun AppBarSearchPreview() {
    StargazerTheme {
        StargazersTopBar(
            title = { Text("Stargazers") },
            navigationIcon = {
                AppBarPainterIcon(
                    painterResource = painterResource(R.drawable.octocat),
                    onClickAction = {}
                )
            },
            actions = {
                AppBarImageVectorIcon(
                    icon = Icons.Outlined.Search,
                    onClickAction = {}
                )
            },
        )
    }
}

@Preview("AppBar Scaffold")
@Composable
private fun AppBarScaffoldPreview() {
    StargazerTheme {
        AppBarScaffold(title = { Text("Stargazers") }, content = {})
    }
}
