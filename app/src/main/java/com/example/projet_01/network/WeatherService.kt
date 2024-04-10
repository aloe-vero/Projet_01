package com.example.projet_01.network

import com.example.projet_01.modele.Weather
import com.example.projet_01.modele.WeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    fun getData(
        @Query("q") city: String,
        @Query("mode") mode: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String
        ): Call<WeatherData>
}