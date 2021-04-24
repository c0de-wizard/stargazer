package com.thomaskioko.stargazers.common.compose.components.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.google.accompanist.coil.CoilImage
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.compositedOnSurface

/**
 * A wrapper around [CoilImage] setting a default [contentScale] and loading placeholder.
 */
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color? = MaterialTheme.colors.compositedOnSurface(0.2f)
) {
    CoilImage(
        data = url,
        modifier = modifier,
        previewPlaceholder = R.drawable.ic_placeholder,
        contentDescription = contentDescription,
        contentScale = contentScale,
        loading = {
            if (placeholderColor != null) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(placeholderColor)
                )
            }
        },
        error = {
            Image(
                painter = painterResource(R.drawable.ic_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Fit,
            )
        }
    )
}
