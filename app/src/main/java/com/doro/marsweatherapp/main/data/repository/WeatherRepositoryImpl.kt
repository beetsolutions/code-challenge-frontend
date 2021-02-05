package com.doro.marsweatherapp.main.data.repository

import com.doro.marsweatherapp.main.data.api.WeatherService
import com.doro.marsweatherapp.main.data.mapper.WeatherInformationMapper
import com.doro.marsweatherapp.main.data.model.ApiResponse
import com.doro.marsweatherapp.main.data.model.Sol
import com.doro.marsweatherapp.main.data.repository.model.Resource
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import com.doro.marsweatherapp.main.domain.repository.WeatherRepository
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class WeatherRepositoryImpl(private val weatherService: WeatherService,
                            private val weatherMapper: WeatherInformationMapper) : WeatherRepository {

    override fun getWeatherDetails(): Flow<Resource<List<WeatherInformation>>> {
        return object : NetworkBoundResource<List<WeatherInformation>, JsonElement>() {

            override suspend fun loadResults(item: JsonElement?): Flow<List<WeatherInformation>> {
                return flow {
                    item?.let { weather ->
                        val gson = Gson()
                        val keys: List<String> = gson.fromJson(weather.asJsonObject.get("sol_keys").toString(), object : TypeToken<List<String>>() {}.type)

                        val toList = keys.map { key ->
                            val sol = gson.fromJson(weather.asJsonObject.get(key).toString(), Sol::class.java)
                            weatherMapper.mapFrom(sol, key)
                        }.toList()
                        emit(toList)
                    }
                }
            }

            override suspend fun createCall(): ApiResponse<JsonElement> {
                return try {
                    ApiResponse.create(weatherService.getWeather())
                } catch (throwable: Throwable) {
                    val error = if (throwable is UnknownHostException) {
                        Throwable(message = "No internet connection available!")
                    } else throwable
                    ApiResponse.create(error)
                }
            }
        }.asFlow()
    }
}