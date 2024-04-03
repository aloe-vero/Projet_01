package com.example.projet_01.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WeatherViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

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

    fun Weather(){
        //TODO
    }

}