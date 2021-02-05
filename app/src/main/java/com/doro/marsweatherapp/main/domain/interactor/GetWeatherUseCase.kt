package com.doro.marsweatherapp.main.domain.interactor

import com.doro.marsweatherapp.main.data.repository.model.Resource
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import com.doro.marsweatherapp.main.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case to get all te weather information
 */
class GetWeatherUseCase(private val repository: WeatherRepository) {
    fun getWeather(): Flow<Resource<List<WeatherInformation>>> = repository.getWeatherDetails()
}