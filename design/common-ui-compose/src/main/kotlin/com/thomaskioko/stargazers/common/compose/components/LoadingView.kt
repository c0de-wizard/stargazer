package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun CircularLoadingView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Preview(
    name = "LoadingView",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "LoadingView • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun LoadingViewPreview() {
    StargazerTheme {
        Surface {
            CircularLoadingView()
        }
    }
}

@Preview(
    name = "LoadingItem",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "LoadingItem • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun LoadingItemPreview() {
    StargazerTheme {
        Surface {
            LoadingItem()
        }
    }
}
