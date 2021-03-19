package com.thomaskioko.stargazers.common.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val LightColors = lightColors(
    primary = colorPrimaryLight,
    primaryVariant = colorPrimaryVariantLight,
    onPrimary = Color.White,
    secondary = colorSecondaryLight,
    secondaryVariant = colorSecondaryVariantLight,
    onSecondary = Color.White,
    error = colorErrorLight
)

private val DarkColors = darkColors(
    primary = colorPrimaryDark,
    primaryVariant = colorPrimaryVariantDark,
    onPrimary = Color.Black,
    secondary = colorSecondaryDark,
    secondaryVariant = colorSecondaryVariantDark,
    onSecondary = Color.White,
    error = colorErrorDark
)

@Composable
fun StargazerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
){
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = StargazersTypography,
        content = content
    )
}

