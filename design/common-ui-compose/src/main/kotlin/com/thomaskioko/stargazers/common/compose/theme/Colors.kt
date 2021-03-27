package com.thomaskioko.stargazers.common.compose.theme

import androidx.compose.material.Colors
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.Dp

val colorPrimaryLight = Color(0xFF344955)
val colorPrimaryVariantLight = Color(0xFF232f34)
val colorSecondaryLight = Color(0xFFf9aa33)
val colorSecondaryVariantLight = Color(0xFFf9be64)
val colorBackgroundLight = Color(0xFFeef0f2)
val colorSurfaceLight = Color(0xFFffffff)
val colorPrimarySurfaceVariantLight = Color(0xFF232f34)
val colorErrorLight = Color(0xFFff4c5d)
val colorOnPrimaryLight = Color(0xFFffffff)
val colorOnSecondaryLight = Color(0xFF99000000)
val colorOnBackgroundLight = Color(0xFF000000)
val colorOnSurfaceLight = Color(0xFF000000)
val colorOnErrorLight = Color(0xFF000000)


val colorPrimaryDark = Color(0xFF212121)
val colorPrimaryVariantDark = Color(0xFF000000)
val colorSecondaryDark = Color(0xFF2188ff)
val colorSecondaryVariantDark = Color(0xFF005cc5)
val colorAccentDark = Color(0xFFf18e33)
val colorBackgroundDark = Color(0xFFf212121)
val colorSurfaceDark = Color(0xFF212121)
val colorPrimarySurfaceVariantDark = Color(0xFF000000)
val colorErrorDark = Color(0xFFF44336)
val colorOnPrimaryDark = Color(0xFFFFFFFF)
val colorOnSecondaryDark = Color(0xFFd1d5da)
val colorOnBackgroundDark = Color(0xFF212121)
val colorOnSurfaceDark = Color(0xFFFFFFFF)
val colorOnErrorDark = Color(0xFFFFFFFF)

val black = Color(0xFF000000)
val favorite = Color(0xFFf9be64)

//Language colors
val css =  Color(0xFF563d7c)
val go =  Color(0xFF375eab)
val kotlin =  Color(0xFFF08E33)
val php =  Color(0xFF4F5D95)
val javaScript =  Color(0xFFf1e05a)
val java =  Color(0xFFB07219)
val swift =  Color(0xFFffac45)
val ruby =  Color(0xFF701516)
val python =  Color(0xFF3572A5)
val other =  Color(0xFFA9A6A0)

/**
 * Return the fully opaque color that results from compositing [onSurface] atop [surface] with the
 * given [alpha]. Useful for situations where semi-transparent colors are undesirable.
 */
@Composable
fun Colors.compositedOnSurface(alpha: Float): Color {
    return onSurface.copy(alpha = alpha).compositeOver(surface)
}

/**
 * Calculates the color of an elevated `surface` in dark mode. Returns `surface` in light mode.
 */
@Composable
fun Colors.elevatedSurface(elevation: Dp): Color {
    return LocalElevationOverlay.current?.apply(
        color = this.surface,
        elevation = elevation
    ) ?: this.surface
}
