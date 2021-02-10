package com.doro.marsweatherapp.main.data.mapper

import com.doro.marsweatherapp.main.data.model.Sol
import com.doro.marsweatherapp.main.domain.model.WeatherInformation
import com.doro.marsweatherapp.main.domain.model.WeatherOverview
import org.threeten.bp.OffsetDateTime
import java.text.SimpleDateFormat
import java.util.*

class WeatherInformationMapper : Mapper<Sol, WeatherInformation> {

    override fun mapFrom(type: Sol, key: String): WeatherInformation {

        val firstDateToEpochMilli = OffsetDateTime.parse(type.firstDate).toZonedDateTime().toInstant().toEpochMilli()
        val lastDateToEpochMilli = OffsetDateTime.parse(type.lastDate).toZonedDateTime().toInstant().toEpochMilli()

        val simpleDateFormat = SimpleDateFormat("d MMM", Locale.getDefault())

        val firstDate = simpleDateFormat.format(firstDateToEpochMilli)
        val lastDate = simpleDateFormat.format(lastDateToEpochMilli)

        return WeatherInformation(
            key = key,
            solName = "Sol $key",
            firstDate = firstDate,
            lastDate = lastDate,
            ordinal = type.ordinal,
            season = type.season,
            pressure = WeatherOverview(
                averageValue = type.pressure?.averageOfSamples,
                recordedSamplesOverSol = type.pressure?.totalNumberOfRecordedSample,
                minValue = type.pressure?.minDataSample,
                maxValue = type.pressure?.maxDataSample
            ),
            wind = WeatherOverview(
                averageValue = type.wind?.averageOfSamples,
                recordedSamplesOverSol = type.wind?.totalNumberOfRecordedSample,
                minValue = type.wind?.minDataSample,
                maxValue = type.wind?.maxDataSample
            ),
            temperature = WeatherOverview(
                averageValue = type.temperature?.averageOfSamples,
                recordedSamplesOverSol = type.temperature?.totalNumberOfRecordedSample,
                minValue = type.temperature?.minDataSample,
                maxValue = type.temperature?.maxDataSample
            ),
            northernSeason = type.northernSeason,
            southernSeason = type.southernSeason
        )
    }
}