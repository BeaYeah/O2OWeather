/**
 * Typography configuration for the app's theme using Jetpack Compose.
 */
package com.beayeah.o2oweather.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Typography configuration for the app's theme.
 */
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default, // Default font family for large body text
        fontWeight = FontWeight.Normal, // Normal font weight
        fontSize = 16.sp, // Font size in scaled pixels (sp)
        lineHeight = 24.sp, // Line height in scaled pixels (sp)
        letterSpacing = 0.5.sp // Letter spacing in scaled pixels (sp)
    )
)
