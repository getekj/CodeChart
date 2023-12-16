package com.example.quickchart.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.quickchart.R

val Kadwa = FontFamily(
    Font(R.font.kadwa_regular),
    Font(R.font.kadwa_bold, FontWeight.Bold)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Normal,
        fontSize = 42.sp
    ),
    titleLarge = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Light,
        fontSize = 28.sp
    ), //PAtient MRN
    bodyMedium = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ), //code chart screen buttons
    labelSmall = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ), //end button
    bodySmall = TextStyle(
        fontFamily = Kadwa,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ) //interventions text

)