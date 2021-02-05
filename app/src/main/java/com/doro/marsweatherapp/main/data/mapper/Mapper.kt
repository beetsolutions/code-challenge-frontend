package com.doro.marsweatherapp.main.data.mapper

/**
 * Model mappers for retrieving of models from outer data source layers
 *
 */
interface Mapper<E, D> {
    fun mapFrom(type: E, key: String): D
}