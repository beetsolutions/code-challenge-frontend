package com.doro.marsweatherapp.main.data.mapper

import com.doro.marsweatherapp.main.data.model.Sol
import org.junit.Assert.*
import org.junit.Test
import org.threeten.bp.format.DateTimeParseException

class WeatherInformationMapperTest {

    private val sol = Sol(firstDate = "2021-08-31T13:10:41Z", lastDate = "2021-01-22T13:10:41Z")
    private val sut = WeatherInformationMapper()

    @Test
    fun `test date formatting`() {
        val result = sut.mapFrom(sol, "773")
        assertEquals(result.firstDate, "31 Aug")
        assertEquals(result.lastDate, "22 Jan")
    }

    @Test
    fun `test sol name formatting`() {
        val result = sut.mapFrom(sol, "773")
        assertEquals(result.solName, "Sol 773")
    }

    @Test(expected = DateTimeParseException::class)
    fun `test should throw exception with wrong date format`() {
        val sol = Sol(firstDate = "2021-08-31", lastDate = "2021-01-22")
        sut.mapFrom(sol, "773")
    }
}