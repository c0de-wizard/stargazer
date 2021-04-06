package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.thomaskioko.stargazers.common.compose.components.util.AnimatingFabContent
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun StargazerFab(
    extended: Boolean,
    isBookmarked: Boolean,
    fabText: String,
    modifier: Modifier = Modifier,
    iconDescription: String? = null,
    onRepoBookMarked: () -> Unit = { }
) {

    key(isBookmarked) { // Prevent multiple invocations to execute during composition
        FloatingActionButton(
            onClick = onRepoBookMarked,
            modifier = modifier
                .padding(16.dp)
                .navigationBarsPadding()
                .height(48.dp)
                .widthIn(min = 48.dp),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onSecondary
        ) {
            AnimatingFabContent(
                icon = {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = iconDescription
                    )
                },
                text = {
                    Text(
                        text = fabText,
                        color = MaterialTheme.colors.onSecondary
                    )
                },
                extended = extended
            )
        }
    }
}

@Preview(
    name = "BookMark Fab",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "BookMark Fab • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun StargazerFabExpandedPreview() {
    StargazerTheme {
        StargazerFab(extended = true, isBookmarked = true, fabText = "Remove Bookmark")
    }
}

@Preview(
    name = "BookMark Fab",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_4_XL
)
@Preview(
    name = "BookMark Fab • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4_XL
)
@Composable
fun StargazerFabCollapsedPreview() {
    StargazerTheme {
        StargazerFab(extended = false, isBookmarked = false, fabText = "Bookmark")
    }
}