package com.thomaskioko.stargazers.common.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thomaskioko.stargazers.common.ui.resources.R

private val WorkSans = FontFamily(
    Font(R.font.work_sans),
    Font(R.font.work_sans_bold, FontWeight.Bold),
    Font(R.font.work_sans_medium, FontWeight.W500),
    Font(R.font.work_sans_semibold, FontWeight.W600)
)

val StargazersTypography = Typography(
    h4 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W600,
        fontSize = 30.sp
    ),
    h5 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W600,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W600,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    body1 = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = WorkSans,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp
    )
)
