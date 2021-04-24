package com.thomaskioko.stargazers.common.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.thomaskioko.stargazers.common.compose.components.util.NetworkImage
import com.thomaskioko.stargazers.common.compose.theme.StargazerTheme

@Composable
fun OutlinedAvatar(
    url: String,
    modifier: Modifier = Modifier,
    outlineSize: Dp = 0.dp,
    outlineColor: Color = MaterialTheme.colors.surface
) {
    Box(
        modifier = modifier.background(
            color = outlineColor,
            shape = CircleShape
        )
    ) {
        NetworkImage(
            url = url,
            contentDescription = null,
            modifier = Modifier
                .padding(outlineSize)
                .fillMaxSize()
                .clip(CircleShape)
        )
    }
}

@Preview(
    name = "Outlined Avatar",
    widthDp = 40,
    heightDp = 40
)
@Composable
private fun OutlinedAvatarPreview() {
    StargazerTheme {
        OutlinedAvatar(url = "")
    }
}
