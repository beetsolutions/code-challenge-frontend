package com.doro.marsweatherapp.main.domain.repository

import com.doro.marsweatherapp.main.data.repository.model.Resource
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getWeatherDetails(): Flow<Resource<List<WeatherInformation>>>
}