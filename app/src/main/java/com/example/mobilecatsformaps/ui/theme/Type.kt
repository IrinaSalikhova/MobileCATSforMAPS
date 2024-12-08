package com.example.mobilecatsformaps.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.example.mobilecatsformaps.R

val myFontFamily = FontFamily(Font(R.font.nunito))

// Define your typography styles
val Typography = Typography(

    displayLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp
    ),
    displaySmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelLarge = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = myFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    )
)

// Additional styles for specific cases
val CurvedTextStyle = TextStyle(
    fontFamily = myFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
    textDecoration = TextDecoration.Underline
)

val LargeTextStyle = TextStyle(
    fontFamily = myFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 40.sp
)