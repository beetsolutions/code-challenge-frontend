package com.doro.marsweatherapp.main.domain.model

class WeatherInformation(
    val key: String,
    val solName: String,
    val firstDate: String,
    val lastDate: String,
    val ordinal: Int? = 0,
    val southernSeason: String? = null,
    val northernSeason: String? = null,
    val season: String? = null,
    val pressure: WeatherOverview? = null,
    val temperature: WeatherOverview? = null,
    val wind: WeatherOverview? = null
) {
    val windAverageFormat =  String.format("Avg: %s", wind?.averageValue?.let { value -> String.format("%.2f", value).toDouble() } ?: "N/A")
    val windMinFormat =  String.format("Low: %s", wind?.minValue?.let { value -> String.format("%.2f", value).toDouble() } ?: "N/A")
    val windMaxFormat =  String.format("High: %s", wind?.maxValue?.let { value -> String.format("%.2f", value).toDouble() } ?: "N/A")

    val pressureAverageFormat = String.format("Avg: %s", pressure?.let { it.averageValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")
    val pressureMinFormat = String.format("Low: %s", pressure?.let { it.minValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")
    val pressureMaxFormat = String.format("High: %s", pressure?.let { it.maxValue?.let { value -> String.format("%.2f", value).toDouble() }} ?: "N/A")

    val temperatureAverageFormat = String.format("Avg: %s", temperature?.averageValue?.toInt()?.toString() ?: "N/A")
    val temperatureMinFormat = String.format("Low: %s", temperature?.minValue?.toInt()?.toString()?: "N/A")
    val temperatureMaxFormat = String.format("High: %s", temperature?.maxValue?.toInt()?.toString()?: "N/A")
}