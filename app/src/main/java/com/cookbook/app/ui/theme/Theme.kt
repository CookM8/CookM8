package com.cookbook.app.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Brand colours matching original design
val OrangeAccent  = Color(0xFFFF6F00)
val OrangeDark    = Color(0xFFF57C00)
val GreenAccent   = Color(0xFF66BB6A)
val GreenDark     = Color(0xFF4CAF50)
val SurfaceLight  = Color(0xFFFAFAFA)
val CardBg        = Color(0xFFFFFFFF)

private val LightColorScheme = lightColorScheme(
    primary        = OrangeAccent,
    onPrimary      = Color.White,
    primaryContainer   = Color(0xFFFFE0B2),
    secondary      = GreenAccent,
    onSecondary    = Color.White,
    secondaryContainer = Color(0xFFDCEDC8),
    surface        = SurfaceLight,
    background     = Color(0xFFF3F4F6),
    onSurface      = Color(0xFF1C1B1F),
    error          = Color(0xFFE63946)
)

@Composable
fun CookbookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography  = Typography(),
        content     = content
    )
}
