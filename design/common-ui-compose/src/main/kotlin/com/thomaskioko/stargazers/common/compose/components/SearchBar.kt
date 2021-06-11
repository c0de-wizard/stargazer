package com.thomaskioko.stargazers.common.compose.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { query ->
            searchQuery = query
            onSearch(query)
        },
        label = {
            Text(
                text = "Search Repository",
                color = MaterialTheme.colors.onSecondary
            )
        },
        textStyle = TextStyle(color = MaterialTheme.colors.onSecondary),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = MaterialTheme.colors.onSecondary,
            textColor = MaterialTheme.colors.onSecondary,
            disabledTextColor = MaterialTheme.colors.onSecondary,
            focusedBorderColor = MaterialTheme.colors.onSecondary,
            unfocusedBorderColor = MaterialTheme.colors.onSecondary,
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = MaterialTheme.colors.onSecondary
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    searchQuery = ""
                    keyboardController?.hide()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Clear",
                    tint = MaterialTheme.colors.onSecondary
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onSearch(searchQuery)
            keyboardController?.hide()
        }),
        modifier = modifier,
    )
}

@Preview(
    name = "Repository Item",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "Repository Item • Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun RepoListPreview() {
    StargazerTheme {
        SearchBar(
            onSearch = {},
            modifier = Modifier
                .fillMaxWidth(.9f)
                .padding(8.dp)
        )
    }
}
