package com.example.projet_01.ui.theme

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
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
    private var _cities = listOf("Montréal", "Laval", "Québec", "Position actuelle")
    private var _selectedCity = mutableStateOf("")
    private var _expanded = mutableStateOf(false)

    fun updateCity(city: String){
        _city.value = city
    }

    fun updateExpanded(expanded: Boolean){
        _expanded.value = expanded
    }

    fun updateSelectedCity(selectedCity: String){
        _selectedCity.value = selectedCity
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

    fun resetCity(){
        _city.value = ""
    }

    fun Weather(city: String){
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
                var data = reponse.body() as WeatherData
                _uiState.update {
                    currentState ->
                    currentState.copy(
                        name = data.name,
                        main = data.main,
                        weather = data.weather
                    )
                }
            }
            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e("Error COMPLETE FAILURE", t.stackTraceToString())
            }
        })
    }

}