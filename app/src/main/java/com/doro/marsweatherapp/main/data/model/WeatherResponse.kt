package com.doro.marsweatherapp.main.data.model

import com.google.gson.annotations.SerializedName

class Sol(
        @SerializedName("First_UTC")
        val firstDate: String,
        @SerializedName("Last_UTC")
        val lastDate: String,
        @SerializedName("Month_ordinal")
        val ordinal: Int,
        @SerializedName("Southern_season")
        val southernSeason: String,
        @SerializedName("Northern_season")
        val northernSeason: String,
        @SerializedName("Season")
        val season: String,
        @SerializedName("PRE")
        val pressure: Weather? = null,
        @SerializedName("AT")
        val temperature: Weather? = null,
        @SerializedName("WD")
        val wind: Weather? = null) {

    class Weather(
            @SerializedName("av")
            val averageOfSamples: Double,
            @SerializedName("ct")
            val totalNumberOfRecordedSample: Int,
            @SerializedName("mn")
            val minDataSample: Double,
            @SerializedName("mx")
            val maxDataSample: Double
    )
}

