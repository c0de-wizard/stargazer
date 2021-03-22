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
import com.thomaskioko.stargazers.common.compose.components.AppBarBackIcon
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import com.thomaskioko.stargazers.settings.R


@Composable
fun SettingsScreen(
    onThemeChanged: () -> Unit = { },
    onBackPressed: () -> Unit = { }
) {
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
                onThemeChanged = onThemeChanged,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        }
    )

}

@Composable
fun SettingsList(
    onThemeChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(start = 2.dp, end = 16.dp)
    ) {
        item {
            ThemeSettingsItem(onThemeChanged)
            AboutSettingsItem()
        }
    }
}

@Composable
private fun ThemeSettingsItem(
    onThemeChanged: () -> Unit,
) {

    var checkedState by remember { mutableStateOf(false) }

    SettingHeaderTitle(
        title = stringResource(R.string.settings_title_ui),
        modifier = Modifier.padding(start = 16.dp, top = 16.dp)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SettingsImage(
            drawableId = R.drawable.ic_palette_24,
            modifier = Modifier.padding(end = 16.dp),
        )

        Column(
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
                .weight(1f),
        ) {
            SettingTitle(stringResource(R.string.settings_title_theme_dark))
            SettingDescription(stringResource(R.string.settings_theme_description))
        }

        Switch(
            checked = checkedState,
            enabled = true,
            onCheckedChange = {
                checkedState = it
                onThemeChanged()
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.secondaryVariant,
                checkedTrackColor = MaterialTheme.colors.secondaryVariant,
            ),
        )
    }

    SettingListDivider()
}

@Composable
private fun AboutSettingsItem() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        SettingHeaderTitle(title = stringResource(R.string.settings_title_info))

        SettingTitle(title = stringResource(R.string.settings_title_about))

        SettingDescription(description = stringResource(R.string.settings_about_description))
    }

    SettingListDivider()
}

@Composable
fun SettingHeaderTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.body2.copy(
            color = MaterialTheme.colors.secondary
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
    )
}

@Composable
fun SettingsImage(drawableId: Int, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(drawableId),
        tint = MaterialTheme.colors.primary,
        contentDescription = null,
        modifier = modifier
            .size(28.dp)
    )
}

@Composable
fun SettingTitle(title: String) {
    Text(title, style = MaterialTheme.typography.subtitle1)
}

@Composable
fun SettingDescription(description: String) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(description, style = MaterialTheme.typography.body2)
    }
}

/**
 * Full-width divider with padding for settings items
 */
@Composable
private fun SettingListDivider() {
    Divider(
        modifier = Modifier.padding(horizontal = 14.dp),
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
    )
}

@Preview("Settings List")
@Composable
fun SettingsPropertyPreview() {
    StargazerTheme {
        SettingsList(onThemeChanged = {})
    }
}
