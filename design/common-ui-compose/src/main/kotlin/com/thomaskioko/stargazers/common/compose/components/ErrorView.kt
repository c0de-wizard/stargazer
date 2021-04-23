package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    errorMessage: String,
    showError: Boolean = errorMessage.isNotBlank(),
    onErrorAction: () -> Unit = { },
) {

    AnimatedVisibility(
        visible = showError,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        coroutineScope.launch {
            val actionResult = snackBarHostState.showSnackbar(
                message = errorMessage,
                actionLabel = "Retry"
            )

            when(actionResult){
                SnackbarResult.ActionPerformed -> { onErrorAction() }
                SnackbarResult.Dismissed -> {}
            }
        }
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

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    StargazerTheme {
        Surface {
            SnackBarErrorRetry(
                snackBarHostState = snackBarHostState,
                coroutineScope = coroutineScope,
                "Something went wrong!!"
            )
        }
    }
}

@Preview(
    name = "ErrorView",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "ErrorView • Dark",
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
