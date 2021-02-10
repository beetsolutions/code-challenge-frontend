package com.doro.marsweatherapp.main.domain.model

import org.junit.Assert.*
import org.junit.Test

class WeatherInformationTest {

    @Test
    fun `If pressure or wind or temperature is null, values should be Not Available`() {
        val sut = WeatherInformation(
            key = "dummy key",
            solName = "dummy name",
            firstDate = "dummy date",
            lastDate = "dummy date"
        )

        assertEquals(sut.windAverageFormat, "Avg: N/A")
        assertEquals(sut.windMaxFormat, "High: N/A")
        assertEquals(sut.windMinFormat, "Low: N/A")

        assertEquals(sut.pressureAverageFormat, "Avg: N/A")
        assertEquals(sut.pressureMaxFormat, "High: N/A")
        assertEquals(sut.pressureMinFormat, "Low: N/A")

        assertEquals(sut.temperatureAverageFormat, "Avg: N/A")
        assertEquals(sut.temperatureMaxFormat, "High: N/A")
        assertEquals(sut.temperatureMinFormat, "Low: N/A")
    }

    @Test
    fun `Show values from pressure`() {
        val sut = WeatherInformation(
            key = "dummy key",
            solName = "dummy name",
            firstDate = "dummy date",
            lastDate = "dummy date",
            pressure = WeatherOverview(
                averageValue = 20.00,
                minValue = 400.10,
                maxValue = 970.1987
            )
        )
        assertEquals(sut.pressureAverageFormat, "Avg: 20.0")
        assertEquals(sut.pressureMaxFormat, "High: 970.2")
        assertEquals(sut.pressureMinFormat, "Low: 400.1")
    }

    @Test
    fun `Show values from temperature`() {
        val sut = WeatherInformation(
            key = "dummy key",
            solName = "dummy name",
            firstDate = "dummy date",
            lastDate = "dummy date",
            temperature = WeatherOverview(
                averageValue = 20.00,
                minValue = 400.10,
                maxValue = 970.1987
            )
        )
        assertEquals(sut.temperatureAverageFormat, "Avg: 20")
        assertEquals(sut.temperatureMaxFormat, "High: 970")
        assertEquals(sut.temperatureMinFormat, "Low: 400")
    }

    @Test
    fun `Show values from wind`() {
        val sut = WeatherInformation(
            key = "dummy key",
            solName = "dummy name",
            firstDate = "dummy date",
            lastDate = "dummy date",
            wind = WeatherOverview(
                averageValue = 20.00,
                minValue = 400.10,
                maxValue = 970.1987
            )
        )
        assertEquals(sut.windAverageFormat, "Avg: 20.0")
        assertEquals(sut.windMaxFormat, "High: 970.2")
        assertEquals(sut.windMinFormat, "Low: 400.1")
    }
}