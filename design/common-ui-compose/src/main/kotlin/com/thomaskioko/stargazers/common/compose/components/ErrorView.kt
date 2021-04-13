package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun SimpleErrorView(errorMessage: String) {
    TextButton(onClick = { }, Modifier.fillMaxSize()) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.error
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SnackBarErrorRetry(
    errorMessage: String,
    showError: Boolean = errorMessage.isNotBlank(),
    onErrorAction: () -> Unit = { },
) {

    AnimatedVisibility(
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        Snackbar(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            content = { Text(errorMessage) },
            action = {
                TextButton(
                    onClick = {
                        onErrorAction()
                    },
                ) {
                    Text(
                        text = "Retry",
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        )
    }
}


@Preview(
    name = "SnackBarError",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "SnackBarError • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun SnackBarErrorPreview() {
    StargazerTheme {
        Surface {
            SnackBarErrorRetry("Something went wrong!!")
        }
    }
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
