package com.example.projet_01.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.projet_01.R
import com.example.projet_01.modele.Clouds
import com.example.projet_01.modele.Coord
import com.example.projet_01.modele.Main
import com.example.projet_01.modele.Sys
import com.example.projet_01.modele.Weather
import com.example.projet_01.modele.WeatherData
import com.example.projet_01.modele.Wind
import com.example.projet_01.network.WeatherService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(WeatherData(
        Coord(0.0, 0.0), listOf(Weather(0, "", "", "")), "",
        Main(0.0, 0.0, 0.0, 0.0, 0, 0), 0,
        Wind(0.0, 0, 0.0),
        Clouds(0), 0,
        Sys(0,0, "", 0, 0), 0, 0, "", 0
    ))
    val uiState: StateFlow<WeatherData> = _uiState.asStateFlow()

    private var _city = mutableStateOf("")
    private var _cities = listOf("Montréal", "Laval", "Québec")
    private var _selectedCity = mutableStateOf("")
    private var _expanded = mutableStateOf(false)
    private var _error = mutableStateOf(false)


    fun updateCity(city: String){
        _city.value = city
    }

    fun updateExpanded(expanded: Boolean){
        _expanded.value = expanded
    }

    fun updateSelectedCity(selectedCity: String){
        _selectedCity.value = selectedCity
    }

    fun updateError(error: Boolean){
        _error.value = error
    }

    fun getCity():String{
        return _city.value
    }

    fun getCities(): List<String>{
        return _cities
    }

    fun getSelectedCity(): String{
        return _selectedCity.value
    }

    fun getExpanded(): Boolean{
        return _expanded.value
    }

    fun getError(): Boolean{
        return _error.value
    }

    fun resetCity(){
        _city.value = ""
    }
    fun getIconCode(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable._01d
            "01n" -> R.drawable._01n
            "02d" -> R.drawable._02d
            "02n" -> R.drawable._02n
            "03d" -> R.drawable._03d
            "03n" -> R.drawable._03n
            "04d" -> R.drawable._04d
            "04n" -> R.drawable._04n
            "09d" -> R.drawable._09d
            "09n" -> R.drawable._09n
            "10d" -> R.drawable._10d
            "10n" -> R.drawable._10n
            "11d" -> R.drawable._11d
            "11n" -> R.drawable._11n
            "13d" -> R.drawable._13d
            "13n" -> R.drawable._13n
            "50d" -> R.drawable._50d
            "50n" -> R.drawable._50n
            else -> R.drawable.unknown
        }
    }

    fun getBackground(iconCode: String): Int {
        return when(iconCode){
            "01d"-> R.drawable._jour
            "01n" -> R.drawable._nuit
            "02d"-> R.drawable._jour
            "02n"-> R.drawable._jour
            "03d"-> R.drawable._jour
            "03n"-> R.drawable._nuit
            "04d"-> R.drawable._pluie
            "04n"-> R.drawable._nuit
            "09d"-> R.drawable._pluie
            "09n"-> R.drawable._nuit
            "10d"-> R.drawable._orage
            "10n"-> R.drawable._orage
            "11d"-> R.drawable._orage
            "11n"-> R.drawable._orage
            "13d"-> R.drawable._neige
            "13n"-> R.drawable._nuit
            "50d"-> R.drawable._neige
            "50n"-> R.drawable._nuit
            else -> R.drawable._neige
        }
    }
    fun weather(city: String){
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service : WeatherService = retrofit.create(WeatherService::class.java)
        Log.d("VILLE", city)
        val requete = service.getData(
            city, "json", "metric", "31c8f080774301a94ffffd4f5348047a", "fr")
        requete.enqueue(object: Callback<WeatherData>{
            override fun onResponse(
                call: Call<WeatherData>,
                reponse: Response<WeatherData>
            ){
                try {
                    val data = reponse.body() as WeatherData
                    _uiState.update {
                            currentState ->
                        currentState.copy(
                            name = data.name,
                            main = data.main,
                            weather = data.weather
                        )
                    }
                }catch(e : NullPointerException){
                    updateError(true)
                    Log.d(TAG, "Error COMPLETE FAILURE")

                }

            }
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("Error COMPLETE FAILURE", t.stackTraceToString())
            }
        })
    }

}