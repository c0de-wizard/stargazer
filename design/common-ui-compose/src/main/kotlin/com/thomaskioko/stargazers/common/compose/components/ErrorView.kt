package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun SimpleErrorView(errorMessage: String) {
    TextButton(onClick = { }, Modifier.fillMaxSize()) {
        Text(errorMessage, textAlign = TextAlign.Center)
    }
}

@Composable
fun SnackBarError() {

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
            SimpleErrorView("Something went wrong!!")
        }
    }
}