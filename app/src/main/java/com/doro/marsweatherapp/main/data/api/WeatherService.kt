package com.doro.marsweatherapp.main.data.api

import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.GET

interface WeatherService {

    @GET("insight_weather/")
    suspend fun getWeather(): Response<JsonElement>
}