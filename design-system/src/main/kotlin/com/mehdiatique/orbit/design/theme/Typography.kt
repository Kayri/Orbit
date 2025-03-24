package com.mehdiatique.orbit.design.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val displayStyle = TextStyle(fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal)
private val headlineStyle = TextStyle(fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal)
private val titleStyle = TextStyle(fontFamily = RobotoFontFamily, fontWeight = FontWeight.SemiBold)
private val bodyStyle = TextStyle(fontFamily = RobotoFontFamily, fontWeight = FontWeight.Normal)
private val labelStyle = TextStyle(fontFamily = RobotoFontFamily, fontWeight = FontWeight.SemiBold)

val OrbitTypography = Typography(
    displayLarge = displayStyle.copy(
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp
    ),
    displayMedium = displayStyle.copy(
        fontSize = 45.sp,
        lineHeight = 52.sp
    ),
    displaySmall = displayStyle.copy(
        fontSize = 36.sp,
        lineHeight = 44.sp
    ),
    headlineLarge = headlineStyle.copy(
        fontSize = 32.sp,
        lineHeight = 40.sp
    ),
    headlineMedium = headlineStyle.copy(
        fontSize = 28.sp,
        lineHeight = 36.sp
    ),
    headlineSmall = headlineStyle.copy(
        fontSize = 24.sp,
        lineHeight = 32.sp
    ),
    titleLarge = titleStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp
    ),
    titleMedium = titleStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = titleStyle.copy(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp
    ),
    bodyLarge = bodyStyle.copy(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = bodyStyle.copy(
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.25.sp
    ),
    bodySmall = bodyStyle.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.4.sp
    ),
    labelLarge = labelStyle.copy(
        fontSize = 14.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.1.sp
    ),
    labelMedium = labelStyle.copy(
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = labelStyle.copy(
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)
