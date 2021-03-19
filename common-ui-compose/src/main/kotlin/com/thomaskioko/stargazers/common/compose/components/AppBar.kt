package com.thomaskioko.stargazers.common.compose.components

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


@Composable
fun AppBar() {
    StargazersScaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(
                        painter = painterResource(R.drawable.octocat),
                        contentDescription = null,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )
                },
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                backgroundColor = MaterialTheme.colors.primarySurface
            )
        }
    ) {

    }

}

@Preview("AppBar")
@Composable
private fun AppBarPreview() {
    StargazerTheme {
        AppBar()
    }
}

@Preview("AppBar • Dark")
@Composable
private fun AppBarDarkPreview() {
    StargazerTheme(darkTheme = true) {
        AppBar()
    }
}
