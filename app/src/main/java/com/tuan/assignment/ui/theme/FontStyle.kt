package com.tuan.assignment.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.tuan.assignment.R

val helveticaFamily = FontFamily(
    Font(R.font.helvetica_normal, FontWeight.Normal),
    Font(R.font.helvetica_bold, FontWeight.Bold)
)

val interFamily = FontFamily(
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold)
)

val poppinsFamily = FontFamily(
    Font(R.font.poppins_medium, FontWeight.Medium)
)

val headline1 = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 16.sp
    )

val headline2 = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp
    )

val title1 = TextStyle(
        fontFamily = helveticaFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 16.sp
    )

val title2 = TextStyle(
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )

val subtitle1 = TextStyle(
    fontFamily = helveticaFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    lineHeight = 16.sp
)

val footer1 = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    lineHeight = 12.sp
)

val rating = TextStyle(
    fontFamily = interFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 10.sp,
    lineHeight = 12.sp
)