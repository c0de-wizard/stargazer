package com.thomaskioko.stargazers.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thomaskioko.stargazers.common.compose.components.AppBarBackIcon
import com.thomaskioko.stargazers.common.compose.components.util.baselineHeight
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.settings.R
import com.thomaskioko.stargazers.settings.domain.UiTheme
import com.thomaskioko.stargazers.settings.ui.SettingsActions.UpdateTheme


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = { }
) {

    val viewModel: SettingsViewModel = viewModel()
    val uiTheme by viewModel.actionState.collectAsState(UiTheme.LIGHT)
    val onThemeChanged: (UiTheme) -> Unit = { viewModel.dispatchAction(UpdateTheme(it)) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Row { Text(text = stringResource(R.string.title_settings)) } },
                navigationIcon = { AppBarBackIcon(onBackPressed = onBackPressed) },
                backgroundColor = MaterialTheme.colors.primarySurface
            )
        },
        content = { innerPadding ->
            SettingsList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )

}

@Composable
fun SettingsList(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 2.dp, end = 16.dp)
    ) {
        item {
            ThemeSettingsItem()
            AboutSettingsItem()
        }
    }
}

@Composable
private fun ThemeSettingsItem() {
    Text(
        text = stringResource(R.string.settings_title_ui),
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.secondary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            .baselineHeight(24.dp),
    )


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                painter = painterResource(R.drawable.ic_palette_24),
                contentDescription = stringResource(R.string.cd_back),
                tint = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(28.dp)
                    .weight(0.5f),
            )
        }

        Column(
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .weight(2f),
        ) {
            Text(
                text = stringResource(R.string.settings_title_theme_dark),
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .fillMaxWidth()
                    .baselineHeight(24.dp),
            )

            Text(
                text = stringResource(R.string.settings_theme_description),
                style = MaterialTheme.typography.caption,
                modifier = Modifier
                    .baselineHeight(18.dp),
            )
        }

        var checkedState by remember { mutableStateOf(false) }

        Switch(
            checked = checkedState,
            enabled = true,
            onCheckedChange = { checkedState = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondaryVariant,
                checkedTrackColor = MaterialTheme.colors.secondaryVariant,
            ),
            modifier = Modifier
                .weight(0.5f),
        )
    }

    Divider(modifier = Modifier.padding(8.dp))
}

@Composable
private fun AboutSettingsItem() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.settings_title_info),
            style = MaterialTheme.typography.body2.copy(
                color = MaterialTheme.colors.secondary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        )


        Text(
            text = stringResource(R.string.settings_title_about),
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .baselineHeight(24.dp),
        )

        Text(
            text = stringResource(R.string.settings_about_description),
            style = MaterialTheme.typography.caption,
            modifier = Modifier
                .fillMaxWidth()
                .baselineHeight(18.dp),
        )
    }

    Divider(modifier = Modifier.padding(start = 8.dp, end = 8.dp))
}


@Preview("Settings List")
@Composable
fun SettingsPropertyPreview() {
    StargazerTheme {
        SettingsList()
    }
}

@Preview("Settings Screen")
@Composable
fun SettingsContentPreview() {
    StargazerTheme {
        SettingsScreen()
    }
}

@Preview("Settings Screen • Dark")
@Composable
fun SettingsContentDarkPreview() {
    StargazerTheme(darkTheme = true) {
        SettingsScreen()
    }
}
