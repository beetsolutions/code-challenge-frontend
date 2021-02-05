package com.doro.marsweatherapp.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doro.marsweatherapp.main.data.repository.model.Resource
import com.doro.marsweatherapp.main.domain.interactor.GetWeatherUseCase
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(private val weatherUseCase: GetWeatherUseCase) : ViewModel() {

    private val _weather = MutableStateFlow<Resource<List<WeatherInformation>>>(Resource.loading())
    val weather: StateFlow<Resource<List<WeatherInformation>>>
        get() = _weather

    init {
        fetchWeatherInformation()
    }

    fun fetchWeatherInformation() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCase.getWeather().collect {
                _weather.value = it
            }
        }
    }
}