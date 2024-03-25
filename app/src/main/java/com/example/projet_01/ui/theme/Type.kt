package com.example.projet_01.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.projet_01.R

val Karla = FontFamily(Font(R.font.karla),Font(R.font.karla_bold))


// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
         fontFamily = Karla,
         fontWeight = FontWeight.Normal,
         fontSize = 30.sp
         ),
    displayMedium = TextStyle(
             fontFamily = Karla,
     fontWeight = FontWeight.Bold,
     fontSize = 25.sp
     ),
 displaySmall = TextStyle(
 fontFamily = Karla,
fontWeight = FontWeight.Bold,
 fontSize = 20.sp
 ),
 bodyLarge = TextStyle(
 fontFamily = Karla,
 fontWeight = FontWeight.Normal,
 fontSize = 16.sp
 )

)