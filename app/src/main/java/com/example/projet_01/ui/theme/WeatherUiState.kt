package com.example.projet_01.ui.theme

import com.example.projet_01.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeatherUiState(
    var currentDate: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date()),
    var temperature: String = "10°C",
    var city: String = "Montréal",
    var icon: Int = R.drawable.icon_peu_nuageux,
    var weather: String = "peu nuageux"
)
