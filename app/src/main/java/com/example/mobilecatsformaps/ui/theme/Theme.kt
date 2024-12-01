package com.example.mobilecatsformaps.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.mobilecatsformaps.ui.theme.*

private val LightColorScheme = lightColorScheme(
    primary = LineColor,
    onPrimary = BackgroundColor,
    secondary = ButtonColor,
    onSecondary = BackgroundColor,
    background = BackgroundColor,
    surface = ElementsColor,
    onSurface = TextColor
)

private val DarkColorScheme = darkColorScheme(

)

/**
 * Theme Wrapper
 */
@Composable
fun MobileCATSforMAPSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}