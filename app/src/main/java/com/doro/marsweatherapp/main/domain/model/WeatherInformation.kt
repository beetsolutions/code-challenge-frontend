package com.doro.marsweatherapp.main.domain.model

class WeatherInformation(
    val key: String,
    val solName: String,
    val firstDate: String,
    val lastDate: String,
    val ordinal: Int,
    val southernSeason: String,
    val northernSeason: String,
    val season: String,
    val pressure: WeatherOverview? = null,
    val temperature: WeatherOverview? = null,
    val wind: WeatherOverview? = null
) {
    val windAverageFormat =  String.format("Avg: %s", wind?.averageValue ?: "N/A")
    val windMinFormat =  String.format("Min: %s", wind?.minValue ?: "N/A")
    val windMaxFormat =  String.format("Max: %s", wind?.maxValue ?: "N/A")

    val pressureAverageFormat = String.format("Avg: %s", pressure?.let { it.averageValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")
    val pressureMinFormat = String.format("Low: %s", pressure?.let { it.maxValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")
    val pressureMaxFormat = String.format("High: %s", pressure?.let { it.minValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")

    val temperatureAverageFormat = String.format("Avg: %s", temperature?.averageValue ?: "N/A")
    val temperatureMinFormat = String.format("Min: %s", temperature?.minValue ?: "N/A")
    val temperatureMaxFormat = String.format("Max: %s", temperature?.maxValue ?: "N/A")
}