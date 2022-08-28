package com.weather.br_weather.repository

import com.weather.br_weather.model.City

interface CityRepositoryInterface {
    suspend fun getCities(): List<City>
    suspend fun addCity(city: City)
    suspend fun deleteCity(city: City)
}