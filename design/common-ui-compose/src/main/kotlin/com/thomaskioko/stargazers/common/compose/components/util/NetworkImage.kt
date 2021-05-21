package com.thomaskioko.stargazers.common.compose.components.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import com.thomaskioko.stargazers.common.compose.R
import com.thomaskioko.stargazers.common.compose.theme.compositedOnSurface

/**
 * A wrapper around [Image] and [rememberCoilPainter], setting a
 * default [contentScale] and showing content while loading.
 */
@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    placeholderColor: Color? = MaterialTheme.colors.compositedOnSurface(0.2f)
) {
    Box(modifier) {
        val painter = rememberCoilPainter(
            request = url,
            previewPlaceholder = R.drawable.ic_placeholder,
        )

        Image(
            painter = painter,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize()
        )

        if (painter.loadState is ImageLoadState.Loading && placeholderColor != null) {
            Spacer(
                modifier = Modifier
                    .matchParentSize()
                    .background(placeholderColor)
            )
        }
    }
}
